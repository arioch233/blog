package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 逻辑删除视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:02
 */
@ApiModel(description = "逻辑删除")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVO {
    /**
     * id列表
     */
    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Integer>")
    @NotNull(message = "文章id不能为空")
    private List<Integer> idList;

    /**
     * 状态值
     */
    @ApiModelProperty(name = "isDelete", value = "删除状态", required = true, dataType = "Integer")
    @NotNull(message = "文章逻辑删除状态不能为空")
    private Integer isDelete;
}