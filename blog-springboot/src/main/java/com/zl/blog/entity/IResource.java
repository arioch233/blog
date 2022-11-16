package com.zl.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源实体类
 *
 * @TableName sys_resource
 */
@TableName(value = "sys_resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IResource implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 权限
     */
    private String permission;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 父权限id
     */
    private Integer parentId;

    /**
     * 是否匿名访问 0否 1是
     */
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}