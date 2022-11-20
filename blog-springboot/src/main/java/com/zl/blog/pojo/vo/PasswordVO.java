package com.zl.blog.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 密码
 *
 * @author 冷血毒舌
 * @create 2022/11/20 15:45
 */
@ApiModel(description = "密码")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVO {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(name = "oldPassword", value = "旧密码", required = true, dataType = "String")
    private String oldPassword;

    /**
     * 新密码
     */
    @Size(min = 6, message = "新密码不能少于6位")
    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(name = "newPassword", value = "新密码", required = true, dataType = "String")
    private String newPassword;

}
