package com.zl.blog.common;

import com.zl.blog.common.enums.StatusCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.zl.blog.common.enums.StatusCodeEnum.FAIL;
import static com.zl.blog.common.enums.StatusCodeEnum.SUCCESS;


/**
 * 接口统一返回的包装类
 *
 * @author 冷血毒舌
 * @description 接口统一返回的包装类
 * @createDate 2021-10-21 00:10:57
 */
@ApiModel(description = "返回包装类")
@Data
public class Result {
    /**
     * 返回码
     */
    @ApiModelProperty(name = "code", value = "返回码", dataType = "Integer")
    private Integer code;
    /**
     * 返回信息
     */
    @ApiModelProperty(name = "message", value = "返回信息", dataType = "String")
    private String message;
    /**
     * 返回数据
     */
    @ApiModelProperty(name = "data", value = "返回数据", dataType = "Object")
    private Object data;


    public static Result success() {
        return restResult(SUCCESS.getCode(), null, SUCCESS.getDescription());
    }

    public static Result success(Object data) {
        return restResult(SUCCESS.getCode(), data, SUCCESS.getDescription());
    }

    public static Result success(String message) {
        return restResult(true, message);
    }

    public static Result success(Object data, String message) {
        return restResult(SUCCESS.getCode(), data, message);
    }

    public static Result error() {
        return restResult(FAIL.getCode(), null, FAIL.getDescription());
    }

    public static Result error(StatusCodeEnum statusCodeEnum) {
        return restResult(statusCodeEnum.getCode(), null, statusCodeEnum.getDescription());
    }

    public static Result error(String message) {
        return restResult(false, message);
    }

    public static Result error(Object data) {
        return restResult(FAIL.getCode(), data, FAIL.getDescription());
    }

    public static Result error(Object data, String message) {
        return restResult(FAIL.getCode(), data, message);
    }

    public static Result error(Integer code, String message) {
        return restResult(code, null, message);
    }

    public static Result error(Integer code, String message, Object data) {
        return restResult(code, data, message);
    }


    /**
     * 返回对象类型1
     *
     * @param flag    标记
     * @param message 消息
     * @return
     */
    private static Result restResult(Boolean flag, String message) {
        Result apiResult = new Result();
        apiResult.setCode(flag ? SUCCESS.getCode() : FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    /**
     * 返回对象类型2
     *
     * @param code    返回码
     * @param data    数据
     * @param message 消息
     * @return
     */
    private static Result restResult(Integer code, Object data, String message) {
        Result apiResult = new Result();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }
}