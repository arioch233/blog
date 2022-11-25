package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 预览文章
 *
 * @author 冷血毒舌
 * @create 2022/11/25 20:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewDTO {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章封面
     */
    private String articleCover;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;
}
