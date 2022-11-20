package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/20 15:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    /**
     * 页面名
     */
    private String pageName;

    /**
     * 页面标签
     */
    private String pageLabel;

    /**
     * 页面封面
     */
    private String pageCover;
}
