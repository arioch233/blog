package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.PageResult;
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
import com.zl.blog.pojo.dto.*;
import com.zl.blog.pojo.vo.ArticleTopVO;
import com.zl.blog.pojo.vo.ArticleVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.DeleteVO;
import com.zl.blog.service.ArticleService;
import com.zl.blog.service.ArticleTagService;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.TagService;
import com.zl.blog.utils.BeanCopyUtils;
import com.zl.blog.utils.CommonUtils;
import com.zl.blog.utils.PageUtils;
import com.zl.blog.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.ARTICLE_SET;
import static com.zl.blog.common.CommonConst.FALSE;
import static com.zl.blog.common.RedisPrefixConst.*;
import static com.zl.blog.common.enums.ArticleStatusEnum.PUBLIC;

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
    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;
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

    @Autowired
    private RedisService redisService;

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
        return articleMapper.listArticles(PageUtils.getLimitCurrent(), PageUtils.getSize());
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture.supplyAsync(() -> articleMapper.listRecommendArticles(articleId));
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList = CompletableFuture.supplyAsync(() -> {
            List<Article> articleList = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                    .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime).eq(Article::getIsDelete, FALSE)
                    .eq(Article::getStatus, PUBLIC.getStatus()).orderByDesc(Article::getId).last("limit 5"));
            return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
        });
        // 查询id对应文章
        ArticleDTO article = articleMapper.getArticleById(articleId);
        if (Objects.isNull(article)) {
            throw new ServiceException("文章不存在");
        }
        // 更新文章浏览量
        updateArticleViewsCount(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover).eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId).last("limit 1"));
        Article nextArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover).eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .gt(Article::getId, articleId).orderByAsc(Article::getId)
                .last("limit 1"));
        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // 封装点赞量和浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        article.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            log.error(StrUtil.format("堆栈信息:{}", ExceptionUtil.stacktraceToString(e)));
        }
        return article;
    }

    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = CommonUtils.castSet(Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class);
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }


    @Override
    public void saveArticleLike(Integer articleId) {
        // 判断是否点赞
        String articleLikeKey = ARTICLE_USER_LIKE + UserUtils.generateUserMd5(request);
        if (redisService.sIsMember(articleLikeKey, articleId)) {
            // 点过赞则删除文章id
            redisService.sRemove(articleLikeKey, articleId);
            // 文章点赞量-1
            redisService.hDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisService.sAdd(articleLikeKey, articleId);
            // 文章点赞量+1
            redisService.hIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Override
    public List<ArticleNewDTO> listArticlesNew() {
        return articleMapper.listArticlesNew();
    }

    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        // 查询文章
        List<ArticlePreviewDTO> articlePreviewDTOList = articleMapper.listArticlesByCondition(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().select(Category::getCategoryName)
                    .eq(Category::getId, condition.getCategoryId())).getCategoryName();
        } else {
            name = tagService.getOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName).eq(Tag::getId, condition.getTagId())).getTagName();
        }
        return ArticlePreviewListDTO.builder().articlePreviewDTOList(articlePreviewDTOList).name(name).build();
    }

    @Override
    public PageResult<ArchiveDTO> listArticleArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 获取文章列表
        Page<Article> articlePage = articleMapper.selectPage(page, new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .orderByDesc(Article::getCreateTime));
        // 转换
        List<ArchiveDTO> archiveList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        // 获取文章数量
        Long count = (long) archiveList.size();
        // 封装数据
        return new PageResult<>(archiveList, count);
    }

    @Override
    public LikeInfoDTO getLikeInfo() {
        String md5 = UserUtils.generateUserMd5(request);
        // 查询账号点赞信息
        Set<Object> articleLikeSet = redisService.sMembers(ARTICLE_USER_LIKE + md5);
        Set<Object> commentLikeSet = redisService.sMembers(COMMENT_USER_LIKE + md5);
        return LikeInfoDTO.builder()
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .build();
    }
}




