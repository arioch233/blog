package com.zl.blog.config.shiro.realm;

import com.zl.blog.config.shiro.token.JwtProvider;
import com.zl.blog.config.shiro.token.JwtToken;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.RoleMapper;
import com.zl.blog.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.zl.blog.common.RedisPrefixConst.USER_TOKEN;

/**
 * 处理token校验
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRealm extends AuthorizingRealm {

    private final JwtProvider jwtProvider;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
     * 自定义授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Claims claims = (Claims) principalCollection.getPrimaryPrincipal();
        Integer userId = (Integer) claims.get("userId");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 默认给一个user角色
        List<String> roles = roleMapper.listRolesByUserInfoId(userId);
        List<String> permissions = roleMapper.listResourcesRoleByUserInfoId(userId);
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 自定义认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String) authenticationToken.getPrincipal();
        // 解码token
        Claims claims = jwtProvider.decodeToken(jwt);
        if (claims == null) {
            throw new IncorrectCredentialsException("Authorization token is invalid");
        }
        // claims放入全局Subject中
        return new SimpleAuthenticationInfo(claims, jwt, "JwtRealm");
    }
}
