package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台标签数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 15:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagBackDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签下的文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
