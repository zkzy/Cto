package com.xiao.conn;


import android.os.Bundle;

import com.xiao.conn.tools.Params;

import org.jetbrains.annotations.NotNull;

public final class Cto {

    private Cto(){}

    public static Request create(@NotNull Class target){
        return create(target,false);
    }

    public static Request create(@NotNull Class target,boolean isDurable){
        Request request = new Request.Build(target).getRequest();
        RequestManager.getInstance().addRequest(request,isDurable);
        return request;
    }

    public static Request.Build params(Params params){
        return new Request.Build(params);
    }

    public static Request.Build call(CallBack callBack){
        return new Request.Build(callBack);
    }

    public static Request.Build bundle(Bundle bundle){
        return new Request.Build(bundle);
    }
}
