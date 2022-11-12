package com.zl.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**

 * 时区枚举
 * @author 冷血毒舌
 * @create 2022/11/8 0:18
 */
@Getter
@AllArgsConstructor
public enum ZoneEnum {

    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");

    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String desc;

}
