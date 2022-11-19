package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 置顶状态视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:04
 */
@ApiModel(description = "置顶")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTopVO {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "文章id", dataType = "Integer")
    @NotNull(message = "文章id不能为空")
    private Integer id;

    /**
     * 置顶状态
     */
    @ApiModelProperty(name = "isTop", value = "置顶状态", required = true, dataType = "Integer")
    @NotNull(message = "文章置顶状态不能为空")
    private Integer isTop;
}
