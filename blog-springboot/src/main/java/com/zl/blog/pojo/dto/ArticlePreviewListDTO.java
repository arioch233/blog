package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 预览文章列表
 *
 * @author 冷血毒舌
 * @create 2022/11/25 19:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewListDTO {

    /**
     * 条件
     */
    private String name;

    /**
     * 预览文章列表
     */
    private List<ArticlePreviewDTO> articlePreviewDTOList;
}
