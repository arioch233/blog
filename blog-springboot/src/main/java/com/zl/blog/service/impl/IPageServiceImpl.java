package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.IPage;
import com.zl.blog.mapper.IPageMapper;
import com.zl.blog.pojo.vo.PageVO;
import com.zl.blog.service.IPageService;
import com.zl.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_page(页面)】的数据库操作Service实现
 * @createDate 2022-11-20 15:50:07
 */
@Service
public class IPageServiceImpl extends ServiceImpl<IPageMapper, IPage>
        implements IPageService {

    @Resource
    private IPageMapper pagesMapper;

    @Override
    public List<PageVO> listPages() {
        return BeanCopyUtils.copyList(pagesMapper.selectList(null), PageVO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePage(PageVO pageVO) {
        IPage pages = BeanUtil.copyProperties(pageVO, IPage.class);
        saveOrUpdate(pages);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePage(Integer pageId) {
        pagesMapper.deleteById(pageId);
    }
}




