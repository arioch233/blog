package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.IResource;
import com.zl.blog.pojo.dto.LabelOptionDTO;
import com.zl.blog.pojo.dto.ResourceDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.ResourceVO;

import java.util.List;

/**
 * 资源服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_resource】的数据库操作Service
 * @createDate 2022-11-15 00:58:33
 */
public interface IResourceService extends IService<IResource> {

    /**
     * 查看资源列表
     *
     * @param conditionVO 条件
     * @return 资源列表
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);

    /**
     * 添加或修改资源
     *
     * @param resourceVO 资源对象
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     */
    void deleteResource(Integer resourceId);

    /**
     * 查看资源选项
     *
     * @return 资源选项
     */
    List<LabelOptionDTO> listResourceOption();
}
