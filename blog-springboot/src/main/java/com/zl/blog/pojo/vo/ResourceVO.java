package com.zl.blog.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/**
 * 资源视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/15 10:38
 */
@ApiModel(description = "资源")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO {
    /**
     * 资源id
     */
    @ApiModelProperty(name = "id", value = "资源id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 资源名
     */
    @NotBlank(message = "资源名不能为空")
    @ApiModelProperty(name = "resourceName", value = "资源名", required = true, dataType = "String")
    private String resourceName;

    /**
     * 权限
     */
    @ApiModelProperty(name = "permission", value = "权限", required = true, dataType = "String")
    private String permission;

    /**
     * 请求方式
     */
    @ApiModelProperty(name = "requestMethod", value = "请求方式", required = true, dataType = "String")
    private String requestMethod;

    /**
     * 父资源id
     */
    @ApiModelProperty(name = "parentId", value = "父资源id", required = true, dataType = "Integer")
    private Integer parentId;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty(name = "isAnonymous", value = "是否匿名访问", required = true, dataType = "Integer")
    private Integer isAnonymous;

}

