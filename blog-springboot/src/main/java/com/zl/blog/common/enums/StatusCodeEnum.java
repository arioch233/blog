package com.zl.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 接口状态码枚举
 *
 * @author 冷血毒舌
 * @create 2022/10/15 22:48
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 未登录
     */
    NO_LOGIN(401, "用户未登录"),
    /**
     * 没有操作权限
     */
    AUTHORIZED(430, "没有操作权限"),
    /**
     * token 失效
     */
    TOKEN_EXIST(450, "token 失效请重新登录"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),
    /**
     * 失败
     */
    FAIL(510, "操作失败"),
    /**
     * 参数校验失败
     */
    VALID_ERROR(520, "参数格式不正确"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(521, "用户名已存在"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST(522, "用户名不存在");


    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;
}
