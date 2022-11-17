package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.entity.Role;
import com.zl.blog.pojo.dto.ResourceRoleDTO;
import com.zl.blog.pojo.dto.RoleDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【sys_role】的数据库操作Service实现
 * @createDate 2022-11-15 10:58:44
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

    /**
     * 查询路由角色列表
     *
     * @return 角色标签
     */
    List<ResourceRoleDTO> listResourceRoles();

    /**
     * 根据用户id获取角色名
     *
     * @param userInfoId
     * @return
     */
    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);

    /**
     * 根据用户id获取权限路径
     *
     * @param userInfo
     * @return
     */
    List<String> listResourcesRoleByUserInfoId(@Param("userInfoId") Integer userInfo);

}




