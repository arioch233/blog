package com.zl.blog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zl.blog.entity.Article;
import com.zl.blog.mapper.ArticleMapper;
import com.zl.blog.mapper.CategoryMapper;
import com.zl.blog.mapper.IPageMapper;
import com.zl.blog.mapper.TagMapper;
import com.zl.blog.pojo.dto.*;
import com.zl.blog.pojo.vo.WebsiteConfigVO;
import com.zl.blog.service.BlogInfoService;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.UniqueViewService;
import com.zl.blog.service.WebsiteConfigService;
import com.zl.blog.utils.BeanCopyUtils;
import com.zl.blog.utils.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.*;
import static com.zl.blog.common.RedisPrefixConst.*;
import static com.zl.blog.common.enums.ArticleStatusEnum.PUBLIC;

/**
 * 博客信息服务实现
 *
 * @author 冷血毒舌
 * @create 2022/11/14 23:46
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {


    @Autowired
    private RedisService redisService;
    @Resource
    private HttpServletRequest request;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private IPageMapper pageMapper;

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private UniqueViewService uniqueViewService;

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        Long viewsCount = Long.parseLong(Optional.ofNullable(count).orElse(0).toString());
        // 查询文章量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE));
        // 查询点赞量
        Long likeCount = 666L;
        // 查询留言量
        Long messageCount = 666L;
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewDTOList = uniqueViewService.listUniqueView();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsDTOList = articleMapper.listArticleStatistics();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategory();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagMapper.selectList(null), TagDTO.class);
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_LIKE_COUNT, 0, 4);
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .viewsCount(viewsCount)
                .likeCount(likeCount)
                .articleCount(articleCount)
                .messageCount(messageCount)
                .uniqueViewDTOList(uniqueViewDTOList)
                .articleStatisticsList(articleStatisticsDTOList)
                .categoryDTOList(categoryDTOList)
                .tagDTOList(tagDTOList)
                .build();
        // 查询文章排行
        if (CollectionUtil.isNotEmpty(articleMap)) {
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return 文章排行
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle)
                        .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        // 获取文章数量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, PUBLIC.getStatus())
                .eq(Article::getIsDelete, FALSE));
        // 获取分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        // 获取标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        String viewsCount = Optional.ofNullable(count).orElse(0).toString();
        // 获取页面图片信息
        List<PageDTO> pageDTOList = pageMapper.listPage();
        // 获取网站配置信息
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        // 封装数据
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageDTOList)
                .build();
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }
}
