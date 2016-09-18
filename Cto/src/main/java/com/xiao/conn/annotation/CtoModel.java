package com.xiao.conn.annotation;


import android.support.annotation.StringRes;

import com.xiao.conn.enu.Expired;
import com.xiao.conn.enu.Method;
import com.xiao.conn.enu.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CtoModel {
    // URL
    @StringRes int url();
    // 请求方式
    Method method() default Method.GET;
    // 标识
    String what() default "";
    // 或许会返回这些类型
    Class[] classes() default {};
    // 超时时间
    int timeOut() default 5000;
    // 重连次数
    int rewiring() default 3;
    //缓存有效时长
    int cacheExpired() default Expired.NEVER;
    //执行方式
    int pattern() default Pattern.FAIL_RETURN_CACHE;
}
