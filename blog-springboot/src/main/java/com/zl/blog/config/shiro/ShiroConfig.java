package com.zl.blog.config.shiro;

import com.zl.blog.config.shiro.filter.ShiroFilter;
import com.zl.blog.config.shiro.filter.UrlFilter;
import com.zl.blog.config.shiro.realm.JwtRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:19
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 不创建内置的session
     */
    @Bean
    public SubjectFactory subjectFactory() {
        return new ShiroDefaultSubjectFactory();
    }

    /**
     * 创建安全管理器
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getManager(JwtRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        // 使用自己的realm
        manager.setRealm(realm);
        return manager;
    }

    /**
     * 授权过滤器
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilter.setSecurityManager(securityManager);
        // 注册jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>(3) {
            {
                put("anon", new AnonymousFilter());
                put("jwt", new ShiroFilter());
                put("logout", new LogoutFilter());
                put("url", new UrlFilter());
            }
        };
        shiroFilter.setFilters(filterMap);
        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<>() {
            {
                // 登录注册放行
                put("/user/login", "anon");
                put("/user/register", "anon");
                put("/user/logout", "jwt");
                // swagger放行
                put("/swagger-ui.html", "anon");
                put("/swagger-resources", "anon");
                put("/v2/api-docs", "anon");
                put("/webjars/springfox-swagger-ui/**", "anon");
                put("/configuration/security", "anon");
                put("/configuration/ui", "anon");
                // knife4j
                put("/doc.html", "anon");
                // 任何请求都需要经过jwt过滤器
                put("/admin/**", "jwt,url");
            }
        };
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

}
