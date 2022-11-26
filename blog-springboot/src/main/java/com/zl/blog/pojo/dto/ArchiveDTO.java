package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 归档数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/25 23:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDTO {

    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
