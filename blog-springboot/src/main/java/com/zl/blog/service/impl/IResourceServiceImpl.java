package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.IResource;
import com.zl.blog.entity.RoleResource;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.IResourceMapper;
import com.zl.blog.mapper.RoleResourceMapper;
import com.zl.blog.pojo.dto.LabelOptionDTO;
import com.zl.blog.pojo.dto.ResourceDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.ResourceVO;
import com.zl.blog.service.IResourceService;
import com.zl.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.FALSE;

/**
 * 资源服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【sys_resource】的数据库操作Service实现
 * @createDate 2022-11-15 00:58:33
 */
@Service
public class IResourceServiceImpl extends ServiceImpl<IResourceMapper, IResource>
        implements IResourceService {


    @Resource
    private IResourceMapper resourceMapper;
    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {
        // 查询资源列表
        List<IResource> resourcesList = resourceMapper.selectList(new LambdaQueryWrapper<IResource>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), IResource::getResourceName, conditionVO.getKeywords()));
        // 获取所有模块
        List<IResource> parentList = listResourceModule(resourcesList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<IResource>> childrenMap = listResourceChildren(resourcesList);
        // 绑定模块下的所有接口
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanUtil.copyProperties(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            childrenMap.remove(item.getId());
            return resourceDTO;
        }).collect(Collectors.toList());
        // 若还有资源未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<IResource> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<ResourceDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanUtil.copyProperties(item, ResourceDTO.class)).toList();
            resourceDTOList.addAll(childrenDTOList);
        }
        return resourceDTOList;
    }

    /**
     * 获取模块下的所有资源
     *
     * @param resourcesList 资源列表
     * @return 模块资源
     */
    private Map<Integer, List<IResource>> listResourceChildren(List<IResource> resourcesList) {
        return resourcesList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(IResource::getParentId));
    }

    /**
     * 获取所有资源模块
     *
     * @param resourcesList 资源列表
     * @return 资源模块列表
     */
    private List<IResource> listResourceModule(List<IResource> resourcesList) {
        return resourcesList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        // 更新资源信息
        IResource resource = BeanUtil.copyProperties(resourceVO, IResource.class);
        this.saveOrUpdate(resource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResource(Integer resourceId) {
        // 查询是否有角色关联
        Long count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getResourceId, resourceId));
        if (count > 0) {
            throw new ServiceException("该资源下存在角色");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceMapper.selectList(new LambdaQueryWrapper<IResource>()
                        .select(IResource::getId).
                        eq(IResource::getParentId, resourceId))
                .stream()
                .map(IResource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        this.removeBatchByIds(resourceIdList);
    }

    @Override
    public List<LabelOptionDTO> listResourceOption() {
        // 查询资源列表
        List<IResource> resourceList = resourceMapper.selectList(new LambdaQueryWrapper<IResource>()
                .select(IResource::getId, IResource::getResourceName, IResource::getParentId)
                .eq(IResource::getIsAnonymous, FALSE));
        // 获取所有模块
        List<IResource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<IResource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<IResource> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .map(resource -> LabelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }
}




