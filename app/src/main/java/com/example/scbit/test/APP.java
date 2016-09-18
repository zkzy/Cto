package com.example.scbit.test;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiao.conn.Admin;
import com.xiao.conn.CtoDefaultAdmin;
import com.xiao.conn.CtoDefaultCache;
import com.xiao.conn.CtoAdmin;
import com.xiao.conn.tools.Params;

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CtoAdmin.setAdmin(new CtoDefaultAdmin(this,getString(R.string.domain)));
        CtoAdmin.clearDefaultCache();

//        CtoAdmin.setAdmin(new Admin() {
//
//            @Override
//            public Context getContext() {
//                return APP.this;
//            }
//
//            @Override
//            public String getDomain() {
//                return getString(R.string.domain);
//            }
//
//            @Override
//            public void commonParams(Params params) {
//
//            }
//
//            @Override
//            public void saveCache(String key, String value) {
//                Log.e("saveCache",key+ " " + value);
//                CtoDefaultCache.save(key, value);
//            }
//
//            @Override
//            public String getCache(String key) {
//                Log.e("getCache",key +" "+ CtoDefaultCache.get(key));
//                return CtoDefaultCache.get(key);
//            }
//
//            @Override
//            public void deleteCache(String key) {
//                CtoDefaultCache.delete(key);
//            }
//
//            @Override
//            public String responseDecrypt(String response) {
//                return null;
//            }
//        });


    }
}
