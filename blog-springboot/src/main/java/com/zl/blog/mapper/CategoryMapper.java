package com.zl.blog.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.blog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.pojo.dto.CategoryBackDTO;
import com.zl.blog.pojo.dto.CategoryDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 冷血毒舌
* @description 针对表【tb_category】的数据库操作Service实现
* @createDate 2022-11-19 15:31:16
*/
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询后台分类
     *
     * @param condition 条件
     * @return {@link Page< CategoryBackDTO >} 分类分页对象
     */
    Page<CategoryBackDTO> listCategoryBack(Page<CategoryBackDTO> page, @Param("condition") ConditionVO condition);

    /**
     * 查询前台分类
     *
     * @return {@link  List <CategoryDTO>} 分类列表
     */
    List<CategoryDTO> listCategory();
}




