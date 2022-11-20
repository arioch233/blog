package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关于我视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/19 23:02
 */
@ApiModel(description = "博客信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeVO {
    /**
     * 关于我内容
     */
    @ApiModelProperty(name = "aboutContent", value = "关于我内容", required = true, dataType = "String")
    private String aboutContent;
}
