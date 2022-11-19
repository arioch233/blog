package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * 文章搜索
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchDTO {
    /**
     * 文章id
     */
    @Id
    private Integer id;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 文章状态
     */
    private Integer status;

}
