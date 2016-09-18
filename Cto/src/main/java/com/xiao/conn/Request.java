package com.xiao.conn;


import android.os.Bundle;

import com.xiao.conn.tools.Params;

import org.jetbrains.annotations.Nullable;

public final class Request {

    /** package */ Params params;

    /** package */ Bundle bundle;

    /** package */ Class targetClass;

    /** package */ CallBack callBack;

    /** package */ boolean isCancel = false;

    /** package */ boolean isDurable = false;

    /** package */ Request() {}

    public void cancel(){
        isCancel = true;
    }

    public boolean isCancel(){return isCancel;}

    public static class Build {

        private Request request= new Request();

        public Build(Class targetClass) {
            request.targetClass = targetClass;
        }

        public Build(Params params) {
            request.params = params;
        }

        public Build(CallBack callBack) {
            request.callBack = callBack;
        }

        public Build(Bundle bundle) {
            request.bundle = bundle;
        }

        public Build params(Params params){
            request.params = params;
            return this;
        }

        public Build bundle(Bundle bundle){
            request.bundle = bundle;
            return this;
        }

        public Build call(@Nullable CallBack callBack){
            request.callBack = callBack;
            return this;
        }

        /** package */ Request getRequest(){
            return request;
        }

        public Request create(Class target){
            return create(target,false);
        }

        public Request create(Class target,boolean isDurable){
            request.targetClass = target;
            RequestManager.getInstance().addRequest(request,isDurable);
            return request;
        }
    }
}
