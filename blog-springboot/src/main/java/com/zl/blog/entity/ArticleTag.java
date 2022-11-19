package com.zl.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章标签实体
 *
 * @TableName tb_article_tag
 */
@TableName(value = "tb_article_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 标签id
     */
    private Integer tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}