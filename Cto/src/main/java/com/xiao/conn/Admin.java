package com.xiao.conn;

import android.content.Context;

import com.xiao.conn.tools.Params;

public abstract class Admin {

    public Admin() {
    }

    /**
     * 上下文
     *
     * @return Context
     */
    public abstract Context getContext();

    /**
     * 域名
     *
     * @return 域名
     */
    public abstract String getDomain();

    /**
     * 添加公共参数
     *
     * @param params 请求参数
     */
    public abstract void commonParams(Params params);

    /**
     * 保存缓存
     *
     * @param key   缓存-键
     * @param value 缓存-值
     */
    public abstract void saveCache(String key, String value);

    /**
     * 获得缓存
     *
     * @param key 缓存-键
     * @return 缓存-值
     */
    public abstract String getCache(String key);

    /**
     * 删除缓存
     */
    public abstract void deleteCache(String key);

    /**
     * 响应结果解密
     *
     * @param response 返回的 结果
     * @return 解密后的结果
     */
    public abstract String responseDecrypt(String response);
}