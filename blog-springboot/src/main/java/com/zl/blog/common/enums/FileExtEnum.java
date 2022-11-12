package com.zl.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件扩展名枚举
 *
 * @author 冷血毒舌
 * @create 2022/11/10 8:29
 */
@Getter
@AllArgsConstructor
public enum FileExtEnum {

    /**
     * gif文件
     */
    GIF(".gif", "gif文件"),

    /**
     * jpg文件
     */
    JPG(".jpg", "jpg文件"),

    /**
     * png文件
     */
    PNG(".png", "png文件"),

    /**
     * Jpeg文件
     */
    JPEG(".jpeg", "jpeg文件"),

    /**
     * wav文件
     */
    WAV(".wav", "wav文件"),

    /**
     * md文件
     */
    MD(".md", "markdown文件"),

    /**
     * txt文件
     */
    TXT(".txt", "txt文件");

    /**
     * 获取文件格式
     *
     * @param extName 扩展名
     * @return {@link FileExtEnum} 文件格式
     */
    public static FileExtEnum getFileExt(String extName) {
        for (FileExtEnum value : FileExtEnum.values()) {
            if (value.getExtName().equalsIgnoreCase(extName)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 扩展名
     */
    private final String extName;

    /**
     * 描述
     */
    private final String desc;

}
