package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 15:04
 */
@ApiModel(description = "标签")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagVO {
    /**
     * 标签id
     */
    @ApiModelProperty(name = "id", value = "标签id", dataType = "Integer")
    private Integer id;
    /**
     * 标签名称
     */
    @ApiModelProperty(name = "tagName", value = "标签名称", dataType = "String")
    private String tagName;
}
