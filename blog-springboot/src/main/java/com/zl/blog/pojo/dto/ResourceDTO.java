package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 资源数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/15 10:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    /**
     * 权限id
     */
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
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 是否匿名访问
     */
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 权限列表
     */
    private List<ResourceDTO> children;
}

