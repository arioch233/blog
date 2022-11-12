package com.zl.blog.handle.exception;

import com.zl.blog.common.Result;
import com.zl.blog.exception.ServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static com.zl.blog.common.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.zl.blog.common.enums.StatusCodeEnum.VALID_ERROR;


/**
 * 全局异常处理
 *
 * @author 冷血毒舌
 * @description 全局异常处理
 * @createDate 2021-10-21 00:02:59
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理服务异常
     *
     * @param serviceException 业务异常
     * @return Result对象 接口异常信息
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result errorHandle(ServiceException serviceException) {
        return Result.error(serviceException.getCode(), serviceException.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param methodArgumentNotValidException 参数校验异常
     * @return Result对象 接口异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result errorHandle(MethodArgumentNotValidException methodArgumentNotValidException) {
        return Result.error(VALID_ERROR.getCode(),
                Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError())
                        .getDefaultMessage());
    }

    /**
     * 处理系统异常
     *
     * @param exception 系统异常
     * @return Result对象 接口异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errorHandle(Exception exception) {
        // 打印异常信息在程序中出错的位置及原因
        exception.printStackTrace();
        return Result.error(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDescription());
    }

}
