package com.xiao.conn;

import com.xiao.conn.tools.CtoTools;

public class CtoAdmin {

    public static void setAdmin(Admin admin){
        RequestManager.getInstance().updateAdmin(admin);
    }

    public static void cancelAll(){
        RequestManager.getInstance().cancelAllRequest();
    }

    public static boolean isConnected(){
        return CtoTools.connectedIF(RequestManager.getInstance().getAdmin().getContext());
    }

    public static void clearDefaultCache(){
        CtoDefaultCache.getInstance().deleteAll();
    }
}
