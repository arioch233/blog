package com.zl.blog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author 冷血毒舌
 * @create 2021/10/21 13:31
 */
@ApiModel(description = "分页对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 记录列表
     */
    @ApiModelProperty(name = "recordList", value = "记录列表", required = true, dataType = "List<T>")
    private List<T> recordList;

    /**
     * 总数
     */
    @ApiModelProperty(name = "count", value = "记录数", required = true, dataType = "Integer")
    private Long count;
}
