package com.xiao.conn;

import android.content.Context;

import com.xiao.conn.tools.Params;


public class CtoDefaultAdmin extends Admin {

    public CtoDefaultAdmin(Context context, String domain) {
        this.context = context;
        this.domain = domain;
    }

    private Context context;
    private String domain;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public void commonParams(Params params) {

    }

    @Override
    public void saveCache(String key, String value) {
        CtoDefaultCache.getInstance().save(key, value);
    }

    @Override
    public String getCache(String key) {
        return CtoDefaultCache.getInstance().get(key);
    }

    @Override
    public void deleteCache(String key) {
        CtoDefaultCache.getInstance().delete(key);
    }

    @Override
    public String responseDecrypt(String response) {
        return null;
    }
}
