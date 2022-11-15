package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.ResourceVO;
import com.zl.blog.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资源控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/15 10:31
 */
@Api(tags = "资源管理模块")
@RestController
@RequestMapping(value = "/admin/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 查询所有资源列表
     *
     * @param conditionVO 条件
     * @return Result List<ResourceDTO>
     */
    @ApiOperation(value = "获取资源列表")
    @GetMapping("/all")
    public Result getResourceList(ConditionVO conditionVO) {
        return Result.success(resourceService.listResources(conditionVO));
    }


    /**
     * 更新或保存修改的资源
     *
     * @param resourceVO
     * @return Result Boolean
     */
    @ApiOperation(value = "更新保存资源")
    @PostMapping("/update")
    public Result saveOrUpdateResource(@RequestBody ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.success();
    }

    /**
     * 删除资源
     *
     * @param resourceId
     * @return Result Boolean
     */
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/delete/{resourceId}")
    public Result deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success();
    }


    /**
     * 查看角色资源选项
     *
     * @return {@link Result} 角色资源选项
     */
    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/role")
    public Result listResourceOption() {
        return Result.success(resourceService.listResourceOption());
    }

}

