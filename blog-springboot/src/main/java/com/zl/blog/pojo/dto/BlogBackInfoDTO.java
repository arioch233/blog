package com.zl.blog.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客后台信息
 *
 * @author 冷血毒舌
 * @create 2022/11/21 0:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogBackInfoDTO {
    /**
     * 访问量
     */
    private Long viewsCount;

    /**
     * 留言量
     */
    private Long messageCount;

    /**
     * 点赞量
     */
    private Long likeCount;

    /**
     * 文章量
     */
    private Long articleCount;

    /**
     * 分类统计
     */
    private List<CategoryDTO> categoryDTOList;

    /**
     * 标签列表
     */
    private List<TagBackDTO> tagDTOList;

    /**
     * 文章统计列表
     */
    private List<ArticleStatisticsDTO> articleStatisticsList;

    /**
     * 一周用户量集合
     */
    private List<UniqueViewDTO> uniqueViewDTOList;

    /**
     * 文章浏览量排行
     */
    private List<ArticleRankDTO> articleRankDTOList;
}
