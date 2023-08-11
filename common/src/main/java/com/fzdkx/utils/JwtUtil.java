package com.fzdkx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2023/8/8 14:37
 */
@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secureKey}")
    private String secureKey;

    @Value("${jwt.ttl}")
    private int ttl;

    /**
     * @return 返回 生成的 Token令牌
     */
    public String createToken(Map<String, String> map) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 在当前时间上增加有效时间
        calendar.add(Calendar.HOUR,ttl);
        // 创建jwt builder
        JWTCreator.Builder jwt = JWT.create();
        // 遍历map ，逐一添入 payload
        map.forEach(jwt::withClaim);
        return jwt.withExpiresAt(calendar.getTime())   // 设置Token过期时间
                .sign(Algorithm.HMAC256(secureKey));    // 设置私钥，及其加密算法
    }

    //验证Token
    public DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(secureKey)).build().verify(token);
    }
}
