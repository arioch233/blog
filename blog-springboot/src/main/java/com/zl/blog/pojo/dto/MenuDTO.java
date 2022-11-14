package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/15 0:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 子菜单列表
     */
    private List<MenuDTO> children;
}