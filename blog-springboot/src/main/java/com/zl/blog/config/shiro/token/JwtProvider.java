package com.zl.blog.config.shiro.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * token 管理
 *
 * @author 冷血毒舌
 * @create 2022/11/16 21:22
 */
@Slf4j
@Getter
@Component
public class JwtProvider {

    @Value("${jwt.expire}")
    private Integer expire;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 生成token
     *
     * @param userId 用户id
     * @return token
     */
    public String createToken(Object userId) {
        return createToken(userId, "");
    }

    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端（移动端、网页端）
     * @return
     */
    public String createToken(Object userId, String clientId) {
        Date validity = new Date((new Date()).getTime() + expire * 1000);
        return Jwts.builder()
                // jwt的主体
                .setSubject(String.valueOf(userId))
                // jwt签发主体
                .setIssuer("blog-admin")
                // 签发时间
                .setIssuedAt(validity)
                // jwt接受对象
                .setAudience(clientId)
                // 放入用户id
                .claim("userId", userId)
                // 自定义信息
                .claim("website", "blog")
                .signWith(SignatureAlgorithm.HS512, this.getSecretKey())
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.info("无效token: " + authToken);
        }
        return false;
    }

    /**
     * 解码token
     *
     * @param token
     * @return
     */
    public Claims decodeToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(this.getSecretKey()).parseClaimsJws(token).getBody();
            // 客户端id
            String clientId = claims.getAudience();
            // 用户id
            Object userId = claims.get("userId");
            log.info("token有效，userId：{}；clientId：{}", userId, clientId);
            return claims;
        }
        log.warn("***该token无效***");
        return null;
    }

    private String getSecretKey() {
        return Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }
}
