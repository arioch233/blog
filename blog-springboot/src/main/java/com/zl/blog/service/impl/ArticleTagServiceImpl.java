package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.ArticleTag;
import com.zl.blog.mapper.ArticleTagMapper;
import com.zl.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_article_tag】的数据库操作Service实现
 * @createDate 2022-11-19 15:08:47
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
        implements ArticleTagService {

}




