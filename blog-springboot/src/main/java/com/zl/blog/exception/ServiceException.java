package com.zl.blog.exception;

import com.zl.blog.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.zl.blog.common.enums.StatusCodeEnum.FAIL;


/**
 * 业务异常
 *
 * @author 冷血毒舌
 * @description 自定义异常
 * @createDate 2021-10-21 00:06:59
 */
@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();
    /**
     * 错误信息
     */
    private final String message;

    /**
     * 错误异常
     *
     * @param message 错误消息
     */
    public ServiceException(String message) {
        this.message = message;
    }

    /**
     * 通用
     *
     * @param statusCodeEnum 异常类型枚举
     */
    public ServiceException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDescription();
    }


}