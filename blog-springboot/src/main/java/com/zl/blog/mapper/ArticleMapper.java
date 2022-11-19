package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.blog.entity.Article;
import com.zl.blog.pojo.dto.ArticleBackDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 冷血毒舌
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2022-11-19 15:46:06
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询后台文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    Page<ArticleBackDTO> listArticlesBack(Page<ArticleBackDTO> page, @Param("condition") ConditionVO condition);
}




