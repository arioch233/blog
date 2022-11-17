package com.zl.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author 冷血毒舌
 * @create 2022/11/15 23:27
 */
@Getter
@AllArgsConstructor
public enum UserAuthStatus {

    UN_DISABLE("启用", 0),
    IS_DISABLE("禁用", 1);

    /**
     * 状态名
     */
    private final String statusName;
    /**
     * 状态值
     */
    private final Integer status;

}
