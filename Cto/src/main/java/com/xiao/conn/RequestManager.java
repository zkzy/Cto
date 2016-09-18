package com.xiao.conn;

import com.xiao.conn.excep.CtoException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * package
 */
final class RequestManager {

    static final private RequestManager requestManager = new RequestManager();

    static public RequestManager getInstance() {
        return requestManager;
    }

    private RequestManager() {
    }

    //一般请求
    final private ExecutorService EXE_GENERAL = Executors.newFixedThreadPool(3);
    final private ArrayList<Request> generalList = new ArrayList<Request>(9) {
        @Override
        public boolean add(Request request) {
            EXE_GENERAL.execute(new RequestRunnable(request));
            return super.add(request);
        }
    };

    //持久请求
    final private ExecutorService EXE_DURABLE = Executors.newFixedThreadPool(1);
    final private ArrayList<Request> durableList = new ArrayList<Request>(3) {
        @Override
        public boolean add(Request request) {
            EXE_DURABLE.execute(new RequestRunnable(request));
            return super.add(request);
        }
    };

    private Admin admin;

    public Admin getAdmin() {
        if (null == admin) {
            throw new CtoException("admin is null");
        }
        return admin;
    }

    /**
     * 更新管理员
     *
     * @param admin
     */
    public void updateAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * 添加请求
     *
     * @param request   请求
     * @param isDurable 是不是持久请求
     */
    public void addRequest(Request request, boolean isDurable) {
        request.isDurable = isDurable;
        if (isDurable) {
            durableList.add(request);
        } else {
            generalList.add(request);
        }
    }

    /**
     * 取消全部一般的请求
     */
    public void cancelAllRequest() {
        for (int i = 0; i < generalList.size(); i++) {
            Request request = generalList.get(i);
            if (null != request) {
                request.cancel();
            }
        }
    }

    void removeRequest(Request request) {
        if (request.isDurable) {
            durableList.remove(request);
        } else {
            generalList.remove(request);
        }
    }
}
