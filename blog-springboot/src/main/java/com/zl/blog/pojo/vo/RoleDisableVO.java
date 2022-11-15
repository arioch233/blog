package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色状态视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/15 11:03
 */
@ApiModel(description = "角色禁用状态")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDisableVO {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "用户id", dataType = "Integer")
    private Integer id;
    /**
     * 是否禁用
     */
    @ApiModelProperty(name = "isDisable", value = "是否禁用", required = true, dataType = "Integer")
    private Integer isDisable;
}
