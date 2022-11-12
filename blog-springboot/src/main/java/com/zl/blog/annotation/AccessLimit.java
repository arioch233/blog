package com.zl.blog.annotation;

import java.lang.annotation.*;

/**
 * redis接口限流
 *
 * @author 冷血毒舌
 * @create 2022/11/6 23:59
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();
}
