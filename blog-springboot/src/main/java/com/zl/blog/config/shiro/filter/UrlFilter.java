package com.zl.blog.config.shiro.filter;

import com.alibaba.fastjson2.JSON;
import com.zl.blog.common.Result;
import com.zl.blog.handle.context.ApplicationContextHolder;
import com.zl.blog.mapper.RoleMapper;
import com.zl.blog.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.zl.blog.common.enums.StatusCodeEnum.AUTHORIZED;

/**
 * @author 冷血毒舌
 * @create 2022/11/17 17:08
 */
@Slf4j
public class UrlFilter extends PathMatchingFilter {


    @Resource
    private RoleMapper roleMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        Integer userId = ShiroUtils.getUserId(Integer.class);
        if (roleMapper == null) {
            roleMapper = ApplicationContextHolder.getContext().getBean(RoleMapper.class);
        }
        List<String> roles = roleMapper.listRolesByUserInfoId(userId);
        List<String> permissions = roleMapper.listResourcesRoleByUserInfoId(userId);
        if (permissions.contains(uri)) {
            log.info("当前用户角色: " + roles + " 访问URI: [" + uri + "] ");
            return true;
        }
        log.info("当前用户角色: " + roles + " 无URI: [" + uri + "] 权限");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        Result result = Result.error(AUTHORIZED);
        httpResponse.getWriter().write(JSON.toJSONString(result));
        return false;
    }
}
