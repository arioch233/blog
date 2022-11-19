package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章的上（下）一篇
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePaginationDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

}
