package com.xiao.conn.enu;

/**
 * 执行方式
 */
public class Pattern {

    /**
     * ...请求执行失败...
     * if(有缓存){
     * 返回数据
     * }else{
     * 返回执行失败
     * }
     */
    public static final int FAIL_RETURN_CACHE = 13;

    /**
     * ...请求未执行之前...
     * if(有缓存){
     * 返回数据
     * }
     * 请求继续执行
     */
    public static final int BEFORE_RETURN_CACHE = 21;

    /**
     * if(有缓存){
     * 中断请求，直接返回数据
     * }else{
     * 请求继续执行
     * }
     */
    public static final int PRIOR_RETURN_CACHE = 19;

}
