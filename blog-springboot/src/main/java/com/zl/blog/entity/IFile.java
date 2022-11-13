package com.zl.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName sys_file
 */
@TableName(value = "sys_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IFile implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件路径
     */
    private String path;

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
     * md5
     */
    private String md5;

    /**
     * 是否禁用链接(1:是  0:否)
     */
    private Integer enable;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}