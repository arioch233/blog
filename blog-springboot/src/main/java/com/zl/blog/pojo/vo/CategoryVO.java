package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 15:35
 */
@ApiModel(description = "分类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    /**
     * 标签id
     */
    @ApiModelProperty(name = "id", value = "分类id", dataType = "Integer")
    private Integer id;
    /**
     * 标签名称
     */
    @ApiModelProperty(name = "tagName", value = "分类名称", dataType = "String")
    private String categoryName;
}

