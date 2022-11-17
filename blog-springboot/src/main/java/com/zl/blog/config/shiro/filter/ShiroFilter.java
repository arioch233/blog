package com.zl.blog.config.shiro.filter;

import com.alibaba.fastjson2.JSON;
import com.zl.blog.common.Result;
import com.zl.blog.common.enums.StatusCodeEnum;
import com.zl.blog.config.shiro.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证url过滤器
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:20
 */
@Slf4j
public class ShiroFilter extends AccessControlFilter {
    /**
     * 跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 对跨域OPTIONS请求放行
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 是否允许通过
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws IOException {
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        if (token == null) {
            return false;
        }
        try {
            getSubject(servletRequest, servletResponse).login(new JwtToken(token));
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 自定义认证失败返回
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        Result result = Result.error(StatusCodeEnum.TOKEN_EXIST);
        httpResponse.getWriter().write(JSON.toJSONString(result));
        return false;
    }


}
