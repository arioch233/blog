package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 资源角色数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/16 0:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRoleDTO {
    /**
     * 资源id
     */
    private Integer id;

    /**
     * 权限
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 角色名
     */
    private List<String> roleList;
}
