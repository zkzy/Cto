package com.xiao.conn;

import com.xiao.conn.enu.Status;

public final class FailError {

    public FailError(boolean error, Status errorType) {
        this.error = error;
        this.errorType = errorType;
        if (Status.CONN_ERROR == errorType){
            this.errorMessage = "连接出错";
        }else if (Status.NET_ERROR == errorType){
            this.errorMessage = "网络未连接";
        }else if (Status.TIME_OUT == errorType){
            this.errorMessage = "连接超时";
        }else{
            this.errorMessage = "未知错误";
        }
    }

    public boolean error;

    public Status errorType;

    public String errorMessage;
}
