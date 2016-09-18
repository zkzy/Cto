package com.xiao.conn;

import com.xiao.conn.annotation.CtoModel;
import com.xiao.conn.enu.Expired;
import com.xiao.conn.enu.Pattern;
import com.xiao.conn.enu.Status;
import com.xiao.conn.excep.CtoException;
import com.xiao.conn.tools.CtoTools;
import com.xiao.conn.tools.ParamsEntry;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * package
 */
final class RequestRunnable implements Runnable {

    private static final String expiredMark = "#expiryDate#";

    public RequestRunnable(Request request) {
        this.request = request;
        this.key = createKey();
    }

    private Request request;

    private boolean isPriorCache, isBeforeCache, isFailCache;
    private String url, method /* */, key, cache, content;
    private Class[] classes;
    private Class target;
    private int timeOut;
    private int rewiring;
    private int expired;
    private HttpURLConnection conn = null;

    @Override
    public void run() {

        if (checkCanceled()) return;

        final CallBack callBack = request.callBack;
        Admin admin = RequestManager.getInstance().getAdmin();
        faceCache(admin);

        assemble(admin, callBack); //解析注解

        if (checkCanceled()) return;
        if (null != callBack) {
            CtoTools.send2UI(new Runnable() {
                @Override
                public void run() {
                    callBack.onBefore();
                }
            });
        }

        if (prior(callBack)) return;//优先分发缓存，分发成功将中断请求，失败继续请求

        before(callBack);

        if (!CtoAdmin.isConnected()) {
            if (null == callBack || checkCanceled()) return;
            if (isFailCache && null != cache) {
                Object t = CtoTools.json2Bean(target, classes, cache);
                if (null != t) {
                    returnSuccess(callBack, cache, t);
                    return;
                }
            }
            returnFail(callBack, new FailError(true, Status.NET_ERROR));
            return;
        }

        if (checkCanceled()) return;

        Status var = connect(admin);
        if (var != Status.CONN_OK) {
            var = rewiring(admin);
        }

        if (null != conn && null != callBack) {
            try {
                callBack.setResponseCode(conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (var == Status.CONN_OK) {
            if (null == callBack || checkCanceled()) return;
            returnSuccess(callBack, content, CtoTools.json2Bean(target, classes, content));
            return;
        }

        if (null == callBack || checkCanceled()) return;
        if (isFailCache && null != cache) {
            Object t = CtoTools.json2Bean(target, classes, cache);
            if (null != t) {
                returnSuccess(callBack, cache, t);
                return;
            }
            returnFail(callBack, new FailError(true, Status.NET_ERROR));
        }
    }

    private void returnSuccess(final CallBack callBack, final String content, final Object t) {
        if (checkCanceled()) return;
        CtoTools.send2UI(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(content);
                callBack.onSuccessBean(t);
                returnAfter(callBack);
            }
        });
    }

    private void returnFail(final CallBack callBack, @NotNull final FailError failError) {
        if (checkCanceled()) return;
        CtoTools.send2UI(new Runnable() {
            @Override
            public void run() {
                callBack.onFail(failError);
                returnAfter(callBack);
            }
        });
    }

    private void returnAfter(final CallBack callBack) {
        RequestManager.getInstance().removeRequest(request);
        callBack.onAfter();
    }

    /**
     * 获得缓存，检查缓存是否过期
     */
    private void faceCache(Admin admin) {
        this.cache = admin.getCache(key);
        if (null != this.cache && this.cache.startsWith(expiredMark)) {
            String[] var = cache.split(expiredMark, -2);
            if (System.currentTimeMillis() > Long.parseLong(var[1])) {
                admin.deleteCache(key);
                cache = null;
            } else {
                cache = var[2];
            }
        } else {
            cache = null;
        }
    }

    /**
     * 重连
     */
    private Status rewiring(Admin admin) {
        Status var = null;
        for (int i = 0; i < rewiring; i++) {
            var = connect(admin);
            if (var == Status.CONN_OK) {
                break;
            }
        }
        return var;
    }

    /**
     * 连接
     */
    private Status connect(Admin admin) {
        try {
            content = getConn();
            String var = admin.responseDecrypt(content);
            if (null != var) {
                content = var;
            }
            if (expired > Expired.NEVER) {
                admin.saveCache(key, expiredMark + (System.currentTimeMillis() + expired) + expiredMark + content);
            }
            return Status.CONN_OK;
        } catch (ConnectException e) {
            e.printStackTrace();
            return Status.CONN_ERROR;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return Status.TIME_OUT;
        } catch (IOException e) {
            e.printStackTrace();
            return Status.CONN_ERROR;
        }
    }

    /**
     * 获得结果
     */
    @NotNull
    private String getConn() throws IOException {
        if (method.equals("GET")) {
            if (null != request.params) {
                url += request.params.getUrlParams(true).toString();
            }
        }
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(timeOut);
        conn.setRequestMethod(method);
        if (method.equals("POST")) {
            conn.setDoOutput(true);
            for (ParamsEntry header : request.params.getHeaders()) {
                conn.setRequestProperty(header.k, header.v);
            }
            if (null != request.params) {
                conn.getOutputStream().write(request.params.getUrlParams(true).toString().getBytes());
            }
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        conn = null;
        return sb.toString();
    }

    /**
     * 之前返回缓存
     */
    private void before(final CallBack call) {
        if (isBeforeCache) {
            if (null != cache) {
                final Object t = CtoTools.json2Bean(target, classes, cache);
                if (null == t) {
                    return;
                }
                if (null == call) return;
                if (checkCanceled()) return;
                CtoTools.send2UI(new Runnable() {
                    @Override
                    public void run() {
                        call.onSuccess(cache);
                        call.onSuccessBean(t);
                    }
                });
            }
        }
    }

    /**
     * 优先分发缓存，并可能中断请求
     *
     * @return 分发成功 或 失败
     */
    private boolean prior(final CallBack call) {
        if (isPriorCache) {
            if (null == cache) {
                return false;
            } else {
                final Object t = CtoTools.json2Bean(target, classes, cache);
                if (null == t) {
                    return false;
                }
                if (null == call) return true;
                if (checkCanceled()) return true;
                CtoTools.send2UI(new Runnable() {
                    @Override
                    public void run() {
                        call.onSuccess(cache);
                        call.onSuccessBean(t);
                        returnAfter(call);
                    }
                });
                return true;
            }
        }
        return false;
    }

    /**
     * 组装请求
     */
    private void assemble(Admin admin, CallBack callBack) {
        boolean isConnModel = false;

        Annotation[] annotations = request.targetClass.getAnnotations();
        for (Annotation var : annotations) {
            if (var instanceof CtoModel) {
                isConnModel = true;
                CtoModel var1 = ((CtoModel) var);
                url = admin.getDomain() + admin.getContext().getString(var1.url());
                method = var1.method().toString();
                classes = var1.classes();
                timeOut = var1.timeOut();
                rewiring = var1.rewiring();
                expired = var1.cacheExpired();
                int pattern = var1.pattern();
                target = request.targetClass;

                isPriorCache = Pattern.PRIOR_RETURN_CACHE == (pattern & Pattern.PRIOR_RETURN_CACHE);
                isBeforeCache = Pattern.BEFORE_RETURN_CACHE == (pattern & Pattern.BEFORE_RETURN_CACHE);
                isFailCache = Pattern.FAIL_RETURN_CACHE == (pattern & Pattern.FAIL_RETURN_CACHE);

                if (null != callBack) {
                    callBack.setWhat(var1.what());
                    callBack.setBundle(request.bundle);
                    callBack.setURL(url);
                    callBack.setMethod(method);
                }
                break;
            }
        }
        if (!isConnModel) {
            throw new CtoException(request.targetClass.getName() + " Not CtoModel annotation class");
        }
    }

    /**
     * 检查请求是否已取消
     *
     * @return 取消 或 没取消
     */
    private boolean checkCanceled() {
        if (request.isCancel) {
            if (null != conn) {
                conn.disconnect();
                conn = null;
            }
            if (null != request.callBack) {
                CtoTools.send2UI(new Runnable() {
                    @Override
                    public void run() {
                        request.callBack.onCanceled();
                    }
                });
            }
            RequestManager.getInstance().removeRequest(request);
        }
        return request.isCancel;
    }

    /**
     * @return 请求生成的键
     */
    @NotNull
    private String createKey() {
        String key = url;
        if (null != request.params) {
            key += request.params.getUrlParams(false).toString();
        }
        return CtoTools.encryptMD5(key);
    }
}
