package com.zl.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.CommonConst;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.Role;
import com.zl.blog.entity.RoleMenu;
import com.zl.blog.entity.RoleResource;
import com.zl.blog.entity.UserRole;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.RoleMapper;
import com.zl.blog.mapper.UserRoleMapper;
import com.zl.blog.pojo.dto.RoleDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.RoleDisableVO;
import com.zl.blog.pojo.vo.RoleVO;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.RoleMenuService;
import com.zl.blog.service.RoleResourceService;
import com.zl.blog.service.RoleService;
import com.zl.blog.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zl.blog.common.RedisPrefixConst.*;

/**
 * 角色服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【sys_role】的数据库操作Service实现
 * @createDate 2022-11-15 10:58:44
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RedisService redisService;

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO conditionVO) {
        List<RoleDTO> roleDTOList = roleMapper.listRoles(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        Long count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords()));
        return new PageResult<>(roleDTOList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 判断角色名重复
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new ServiceException("角色名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE)
                .build();
        this.saveOrUpdate(role);
        // 更新角色资源关系
        if (Objects.nonNull(roleVO.getResourceIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVO.getId()));
            }
            List<RoleResource> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
        }
        // 更新角色菜单关系
        if (Objects.nonNull(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleVO.getId()));
            }
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        this.saveOrUpdate(role);
        redisService.del(USER_INFO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleDisable(RoleDisableVO roleDisableVO) {
        this.saveOrUpdate(BeanUtil.copyProperties(roleDisableVO, Role.class));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(List<Integer> roleIds) {
        // 判断是否存在用户在该角色下
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIds));
        if (count > 0) {
            throw new ServiceException("该用户下存在用户");
        }
        // 删除角色
        this.removeBatchByIds(roleIds);
    }


    @Override
    public List<String> listRolesByUserInfoId(Integer userId) {
        List<String> roles = JSON.parseArray((String) redisService.hGet(USER_INFO, USER_ROLE + userId), String.class);
        if (Objects.isNull(roles)) {
            roles = roleMapper.listRolesByUserInfoId(userId);
            redisService.hSet(USER_INFO, USER_ROLE + userId, JSON.toJSONString(roles));
        }
        return roles;
    }

    @Override
    public List<String> listResourcesRoleByUserInfoId(Integer userId) {
        List<String> permissions = JSON.parseArray((String) redisService.hGet(USER_INFO, USER_PERMISSION + userId), String.class);
        if (Objects.isNull(permissions)) {
            permissions = roleMapper.listResourcesRoleByUserInfoId(userId);
            redisService.hSet(USER_INFO, USER_PERMISSION + userId, JSON.toJSONString(permissions));
        }
        return permissions;
    }
}




