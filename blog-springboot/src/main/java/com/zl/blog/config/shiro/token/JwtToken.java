package com.zl.blog.config.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义shiro的token
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:21
 */
public class JwtToken implements AuthenticationToken {

    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}