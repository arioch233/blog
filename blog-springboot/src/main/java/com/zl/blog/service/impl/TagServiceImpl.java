package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.ArticleTag;
import com.zl.blog.entity.Tag;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.ArticleTagMapper;
import com.zl.blog.mapper.TagMapper;
import com.zl.blog.pojo.dto.TagBackDTO;
import com.zl.blog.pojo.dto.TagDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.TagVO;
import com.zl.blog.service.TagService;
import com.zl.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标签服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_tag】的数据库操作Service实现
 * @createDate 2022-11-19 14:59:43
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public Page<TagBackDTO> listTagsBack(ConditionVO conditionVO) {
        return tagMapper.listTagsBack(new Page<>(conditionVO.getCurrent(), conditionVO.getSize()), conditionVO);
    }

    @Override
    public List<TagBackDTO> listTagBySearch(ConditionVO conditionVO) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (conditionVO.getKeywords() != null) {
            queryWrapper.like("tag_name", conditionVO.getKeywords());
        }
        List<Tag> tagList = list(queryWrapper);
        List<TagBackDTO> tagDTOList = new ArrayList<>();
        BeanCopyUtils.copyList(tagList, tagDTOList, TagBackDTO.class);
        return tagDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new ServiceException("该分类名已存在");
        }
        Tag tag = new Tag();
        BeanUtil.copyProperties(tagVO, tag);
        this.saveOrUpdate(tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTags(List<Integer> tagIds) {
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getTagId, tagIds));
        if (count > 0) {
            throw new ServiceException("删除失败，该标签下存在文章");
        }
        this.removeBatchByIds(tagIds);
    }

    // 前台
    @Override
    public PageResult<TagDTO> listTags() {
        // 查询标签列表
        List<Tag> tagList = tagMapper.selectList(null);
        // 转换
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList, TagDTO.class).stream()
                .map(item -> TagDTO.builder()
                        .id(item.getId())
                        .tagName(item.getTagName())
                        .articleCount(articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                                .eq(ArticleTag::getTagId, item.getId())))
                        .build())
                .toList();
        // 查询标签数量
        Long count = tagMapper.selectCount(null);
        return new PageResult<>(tagDTOList, count);
    }
}




