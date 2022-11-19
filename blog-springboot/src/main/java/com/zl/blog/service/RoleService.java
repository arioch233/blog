package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.Role;
import com.zl.blog.pojo.dto.RoleDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.RoleDisableVO;
import com.zl.blog.pojo.vo.RoleVO;

import java.util.List;

/**
 * 角色服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_role】的数据库操作Service
 * @createDate 2022-11-15 10:58:44
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 更新角色状态
     *
     * @param roleDisableVO 角色状态
     */
    void updateRoleDisable(RoleDisableVO roleDisableVO);

    /**
     * 删除角色
     *
     * @param roleIds 角色id列表
     */
    void deleteRole(List<Integer> roleIds);

    /**
     * 根据用户id 获取角色
     *
     * @param userId
     * @return
     */
    List<String> listRolesByUserInfoId(Integer userId);

    /**
     * 根据用户id 获取权限
     *
     * @param userId
     * @return
     */
    List<String> listResourcesRoleByUserInfoId(Integer userId);
}
