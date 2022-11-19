package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.Category;
import com.zl.blog.pojo.dto.CategoryDTO;
import com.zl.blog.pojo.vo.CategoryVO;
import com.zl.blog.pojo.vo.ConditionVO;

import java.util.List;

/**
 * 分类服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_category】的数据库操作Service
 * @createDate 2022-11-19 15:31:16
 */
public interface CategoryService extends IService<Category> {

    Page<CategoryDTO> listCategoryBack(ConditionVO conditionVO);

    List<CategoryDTO> listCategoryBySearch(ConditionVO conditionVO);

    void saveOrUpdateCategory(CategoryVO categoryVO);

    void deleteCategory(List<Integer> categoryIds);
}
