package com.xiao.conn.enu;

/**
 * 缓存时效
 */
public class Expired {
    //绝不缓存（默认）
    public static final int NEVER = 0;
    //30分钟
    public static final int M30 = 1000 * 60 * 30;
    //60分钟
    public static final int M60 = M30 * 2;
    //12小时
    public static final int H12 = M60 * 12;
    //24小时
    public static final int H24 = H12 * 2;
    //永远（其实也就一年，但足矣）
    public static final int FOREVER = H24 * 365;

}
