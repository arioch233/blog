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
 * 角色资源实体类
 *
 * @TableName sys_role_resource
 */
@TableName(value = "sys_role_resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer resourceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}