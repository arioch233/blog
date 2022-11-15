package com.zl.blog.controller;


import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.MenuVO;
import com.zl.blog.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 菜单控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/15 0:15
 */
@Api(tags = "菜单模块")
@RestController
@RequestMapping(value = "/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有菜单列表
     *
     * @param conditionVO 条件
     * @return {@link Result List<MenuDTO>}
     */
    @ApiOperation(value = "获取菜单列表")
    @GetMapping("/all")
    public Result listMenus(ConditionVO conditionVO) {
        return Result.success(menuService.listMenus(conditionVO));
    }

    /**
     * 新增或修改保存菜单
     *
     * @param menuVO
     */
    @ApiOperation(value = "更新保存菜单")
    @PostMapping("/update")
    public Result saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.success();
    }

    /**
     * 删除菜单
     *
     * @param menuId
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/delete/{menuId}")
    public Result deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 查看角色菜单选项
     *
     * @return {@link Result List<LabelOptionDTO>} 查看角色菜单选项
     */
    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/role")
    public Result listMenuOptions() {
        return Result.success(menuService.listMenuOptions());
    }

    /**
     * 查看当前用户菜单
     *
     * @return {@link Result List<UserMenuDTO>} 菜单列表
     */
    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/user")
    public Result listUserMenus() {
        return Result.success(menuService.listUserMenus());
    }

}