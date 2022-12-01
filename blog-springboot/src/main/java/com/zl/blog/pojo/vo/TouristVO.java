package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游客信息视图对象
 *
 * @author 冷血毒舌
 * @create 2022/11/29 20:20
 */
@ApiModel(description = "游客信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouristVO {

    /**
     * 游客昵称
     */
    @ApiModelProperty(name = "touristNickname", value = "游客昵称", dataType = "String")
    private String touristNickname;

    /**
     * 游客头像
     */
    @ApiModelProperty(name = "touristAvatar", value = "游客头像", dataType = "String")
    private String touristAvatar;

    /**
     * 游客邮箱
     */
    @ApiModelProperty(name = "touristEmail", value = "游客邮箱", dataType = "String")
    private String touristEmail;
}
