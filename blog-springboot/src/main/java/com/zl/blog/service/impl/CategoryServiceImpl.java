package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.Article;
import com.zl.blog.entity.Category;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.ArticleMapper;
import com.zl.blog.mapper.CategoryMapper;
import com.zl.blog.pojo.dto.CategoryBackDTO;
import com.zl.blog.pojo.vo.CategoryVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.service.CategoryService;
import com.zl.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 分类服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_category】的数据库操作Service实现
 * @createDate 2022-11-19 15:31:16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<CategoryBackDTO> listCategoryBack(ConditionVO conditionVO) {
        return categoryMapper.listCategoryBack(new Page<>(conditionVO.getCurrent(), conditionVO.getSize()), conditionVO);
    }

    @Override
    public List<CategoryBackDTO> listCategoryBySearch(ConditionVO conditionVO) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (conditionVO.getKeywords() != null) {
            queryWrapper.like("category_name", conditionVO.getKeywords());
        }
        List<Category> categoryList = list(queryWrapper);
        List<CategoryBackDTO> categoryDTOList = new ArrayList<>();
        BeanCopyUtils.copyList(categoryList, categoryDTOList, CategoryBackDTO.class);
        return categoryDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new ServiceException("该分类名已存在");
        }
        Category category = new Category();
        BeanUtil.copyProperties(categoryVO, category);
        this.saveOrUpdate(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(List<Integer> categoryIds) {
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>().in(Article::getCategoryId, categoryIds));
        if (count > 0) {
            throw new ServiceException("删除失败，该分类下存在文章");
        }
        this.removeBatchByIds(categoryIds);
    }
}




