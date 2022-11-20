package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.IPage;
import com.zl.blog.pojo.vo.PageVO;

import java.util.List;

/**
 * 页面服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_page(页面)】的数据库操作Service
 * @createDate 2022-11-20 15:50:07
 */
public interface IPageService extends IService<IPage> {

    /**
     * 获取页面列表
     *
     * @return {@link List<PageVO>}
     */
    List<PageVO> listPages();

    /**
     * 保存和更新页面
     *
     * @param pageVO
     */
    void saveOrUpdatePage(PageVO pageVO);

    /**
     * 根据id删除页面
     *
     * @param pageId
     */
    void deletePage(Integer pageId);
}
