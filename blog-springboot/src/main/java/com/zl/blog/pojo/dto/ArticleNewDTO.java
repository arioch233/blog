package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 最新文章数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/24 20:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleNewDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String articleTitle;
}
