package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.enums.ArticleStatusEnum;
import com.zl.blog.entity.Article;
import com.zl.blog.entity.ArticleTag;
import com.zl.blog.entity.Category;
import com.zl.blog.entity.Tag;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.ArticleMapper;
import com.zl.blog.mapper.ArticleTagMapper;
import com.zl.blog.mapper.CategoryMapper;
import com.zl.blog.mapper.TagMapper;
import com.zl.blog.pojo.dto.ArticleBackDTO;
import com.zl.blog.pojo.dto.ArticleDTO;
import com.zl.blog.pojo.dto.ArticleHomeDTO;
import com.zl.blog.pojo.dto.ArticleSearchDTO;
import com.zl.blog.pojo.vo.ArticleTopVO;
import com.zl.blog.pojo.vo.ArticleVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.DeleteVO;
import com.zl.blog.service.ArticleService;
import com.zl.blog.service.ArticleTagService;
import com.zl.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.FALSE;

/**
 * 文章服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2022-11-19 15:46:06
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private TagMapper tagMapper;
    @Autowired
    private TagService tagService;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public Page<ArticleBackDTO> listArticlesBack(ConditionVO conditionVO) {
        return articleMapper.listArticlesBack(new Page<>(conditionVO.getCurrent(), conditionVO.getSize()), conditionVO);
    }

    @Override
    public ArticleVO getBackArticleById(Integer articleId) {
        // 查询文章信息
        Article article = articleMapper.selectById(articleId);
        // 查询文章分类
        Category category = categoryMapper.selectById(article.getCategoryId());
        String categoryName = null;
        if (Objects.nonNull(category)) {
            categoryName = category.getCategoryName();
        }
        // 查询文章标签
        List<String> tagNameList = tagMapper.listTagNameByArticleId(articleId);
        // 封装数据
        ArticleVO articleVO = new ArticleVO();
        BeanUtil.copyProperties(article, articleVO);
        articleVO.setCategoryName(categoryName);
        articleVO.setTagNameList(tagNameList);
        return articleVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 保存文章分类
        Category category = saveArticleCategory(articleVO);
        // 保存或修改文章
        Article article = new Article();
        BeanUtil.copyProperties(articleVO, article);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        // 设置默认文章封面
        if (StrUtil.isBlank(article.getArticleCover())) {
            try {
                article.setArticleCover("");
            } catch (Exception e) {
                throw new ServiceException("设置默认封面失败");
            }
        }
        // 保存文章至数据库
        saveOrUpdate(article);
        // 保存文章标签
        saveArticleTags(articleVO, article.getId());
    }

    /**
     * 保存文章分类
     *
     * @param articleVO
     * @return
     */
    private Category saveArticleCategory(ArticleVO articleVO) {
        // 判断分类是否存在
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getCategoryName, articleVO.getCategoryName()));
        if (Objects.isNull(category) && !articleVO.getStatus().equals(ArticleStatusEnum.DRAFT.getStatus())) {
            category = new Category();
            category.setCategoryName(articleVO.getCategoryName());
            categoryMapper.insert(category);
        }
        return category;
    }

    /**
     * 保存文章标签
     *
     * @param articleVO
     * @param articleId
     */
    private void saveArticleTags(ArticleVO articleVO, Integer articleId) {
        // 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId())) {
            articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        // 添加文章标签
        List<String> tagNameList = articleVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>().in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream().map(Tag::getTagName).toList();
            List<Integer> existTagIdList = new java.util.ArrayList<>(existTagList.stream().map(Tag::getId).toList());
            // 对比已存在的 tag 列表，已存在的从 tagNameList 中删除
            tagNameList.removeAll(existTagNameList);
            if (!CollectionUtils.isEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder().tagName(item).build()).toList();
                // 插入新的 tag
                tagService.saveBatch(tagList);
                List<Integer> tagIdList = tagList.stream().map(Tag::getId).toList();
                existTagIdList.addAll(tagIdList);
            }
            // 构建 article 与 tag 间的关系
            List<ArticleTag> articleTagList = existTagIdList.stream().map(item -> ArticleTag.builder().articleId(articleId).tagId(item).build()).toList();
            articleTagService.saveBatch(articleTagList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticleTopStatus(ArticleTopVO articleTopVO) {
        // 修改文章置顶状态
        Article article = Article.builder().id(articleTopVO.getId()).isTop(articleTopVO.getIsTop()).build();
        this.articleMapper.updateById(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticleById(List<Integer> articleIds) {
        // 删除文章与标签的关连
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, articleIds));
        this.removeBatchByIds(articleIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void logicDeleteArticleById(DeleteVO deleteVO) {
        // 修改文章逻辑删除状态
        List<Article> articleList = deleteVO.getIdList().stream().map(id -> Article.builder().id(id).isTop(FALSE).isDelete(deleteVO.getIsDelete()).build()).collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Override
    public List<ArticleHomeDTO> listArticles() {
        return null;
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        return null;
    }

    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO conditionVO) {
        return null;
    }

    @Override
    public void saveArticleLike(Integer articleId) {

    }
}




