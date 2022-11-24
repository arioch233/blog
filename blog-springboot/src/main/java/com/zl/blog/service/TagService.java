package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.Tag;
import com.zl.blog.pojo.dto.TagBackDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.TagVO;

import java.util.List;

/**
 * 标签服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_tag】的数据库操作Service
 * @createDate 2022-11-19 14:59:43
 */
public interface TagService extends IService<Tag> {

    Page<TagBackDTO> listTagsBack(ConditionVO conditionVO);

    List<TagBackDTO> listTagBySearch(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTags(List<Integer> tagIds);

    // 前台

    /**
     * 标签列表
     *
     * @return
     */
    List<TagBackDTO> listTags();
}
