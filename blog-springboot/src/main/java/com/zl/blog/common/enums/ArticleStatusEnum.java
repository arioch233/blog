package com.zl.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:24
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    PUBLIC(1, "发布"),
    SECRET(2, "私密"),
    DRAFT(3, "草稿");

    private final Integer status;
    private final String description;

}
