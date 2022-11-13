package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台文件
 *
 * @author 冷血毒舌
 * @create 2022/11/13 0:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileBackDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小(KB)
     */
    private Long size;

    /**
     * 文件链接
     */
    private String url;

    /**
     * 是否禁用链接(1:是  0:否)
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
