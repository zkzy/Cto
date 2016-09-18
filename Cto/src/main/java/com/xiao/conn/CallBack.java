package com.xiao.conn;

import android.os.Bundle;
import android.support.annotation.UiThread;

abstract public class CallBack {

    private String url = null;
    private String method = null;
    private String what = null;
    private Bundle bundle = null;
    private int responseCode = -1;

    final /** package */
    void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    final public int getResponseCode() {
        return responseCode;
    }

    final /** package */
    void setURL(String url) {
        this.url = url;
    }

    final public String getURL() {
        return url;
    }

    final /** package */
    void setMethod(String method) {
        this.method = method;
    }

    final public String getMethod() {
        return method;
    }

    final /** package */
    void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    final public Bundle getBundle() {
        return bundle;
    }

    final /** package */
    void setWhat(String what) {
        this.what = what;
    }

    final public String getWhat() {
        return what;
    }

    @UiThread
    public void onBefore() {
    }

    @UiThread
    public void onSuccess(String content) {
    }

    @UiThread
    abstract public void onSuccessBean(Object bean);

    @UiThread
    public void onFail(FailError error) {
    }

    @UiThread
    public void onCanceled() {
    }

    @UiThread
    public void onAfter() {
    }

}
