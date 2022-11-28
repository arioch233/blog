package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 点赞信息数据传输对象
 *
 * @author 冷血毒舌
 * @create 2022/11/28 21:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeInfoDTO {
    /**
     * 点赞文章集合
     */
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    private Set<Object> commentLikeSet;
}
