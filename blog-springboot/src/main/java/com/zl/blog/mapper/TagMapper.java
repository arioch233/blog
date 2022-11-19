package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.blog.entity.Tag;
import com.zl.blog.pojo.dto.TagDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【tb_tag】的数据库操作Service实现
 * @createDate 2022-11-19 14:59:43
 */
public interface TagMapper extends BaseMapper<Tag> {


    /**
     * 查询后台标签
     *
     * @param condition 条件
     * @return {@link Page<TagDTO>} 标签列表
     */
    Page<TagDTO> listTagsBack(Page<TagDTO> page, @Param("condition") ConditionVO condition);

    /**
     * 根据文章id查询标签名
     *
     * @param articleId 文章id
     * @return {@link List <String>} 标签名列表
     */
    List<String> listTagNameByArticleId(Integer articleId);
}




