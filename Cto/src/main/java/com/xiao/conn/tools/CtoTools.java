package com.xiao.conn.tools;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.security.MessageDigest;

public class CtoTools {

    public final static JKson JSON = JKson.Default();

    /**
     * MD5 加密
     *
     * @param data
     * @return
     */
    public static String encryptMD5(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();

            for (int i = 0; i < encryption.length; i++) {

                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));

                else strBuf.append(Integer.toHexString(0xff & encryption[i]));
            }

            return strBuf.toString();

        } catch (Exception e) {
            return "";
        }
    }

    public static <T> T json2Bean(Class<T> target, Class<T>[] classes, String content) {
        if (!isJSON(content)){
            Log.e("CtoException","这可能不是标准JSON："+content);
        }
        T t;
        if (isArray(content)) {
            content = array2Obj(content);
        }
        t = JKson.Default().from(content, target);

        if (t == null) {
            for (Class<T> c : classes) {
                t = JKson.Default().from(content, c);
                if (t != null) break;
            }
        }
        return t;
    }

    static boolean isJSON(String content){
        return (content.startsWith("{") && content.endsWith("}")) || (content.startsWith("[") && content.endsWith("]"));
    }

    static boolean isArray(String json) {
        return json.startsWith("[") && json.endsWith("]");
    }

    static String array2Obj(String array) {
        return "{\"list\":" + array + "}";
    }

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public static void send2UI(Runnable runnable){
        mainHandler.post(runnable);
    }

    public static boolean connectedIF(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected() && ni.getState() == NetworkInfo.State.CONNECTED;

        //return true;
    }

    public static boolean isSDCardCanUse(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
