package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.RoleDisableVO;
import com.zl.blog.pojo.vo.RoleVO;
import com.zl.blog.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/15 11:00
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping(value = "/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @param conditionVO 条件
     * @return Result List<RoleDTO>
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/all")
    public Result getRoleList(ConditionVO conditionVO) {
        return Result.success(roleService.listRoles(conditionVO));
    }


    /**
     * 保存和更新角色信息
     *
     * @param roleVO
     * @return Result Boolean
     */
    @ApiOperation(value = "更新角色信息")
    @PostMapping("/update")
    public Result saveOrUpdateRole(@RequestBody RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success();
    }

    /**
     * 更新角色状态
     *
     * @param roleDisableVO
     * @return
     */
    @ApiOperation(value = "更新角色状态")
    @PostMapping("/update/disable")
    public Result updateRoleDisable(@RequestBody RoleDisableVO roleDisableVO) {
        roleService.updateRoleDisable(roleDisableVO);
        return Result.success();
    }

    /**
     * 根据id删除角色
     *
     * @param roleIds
     * @return Result Boolean
     */

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete")
    public Result deleteRoles(@RequestBody List<Integer> roleIds) {
        roleService.deleteRole(roleIds);
        return Result.success();
    }

}