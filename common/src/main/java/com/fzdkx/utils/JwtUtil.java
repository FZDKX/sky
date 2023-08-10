package com.fzdkx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private long ttl;

    /**
     * @param userInfo 用户信息 的 Json字符串
     * @param authList 权限信息
     * @return 返回 生成的 Token令牌
     */
    public String createToken(String userInfo , List<String> authList){
        // Token的创建时间
        Date now = new Date();
        // Token的过期时间 默认两小时
        Date expireTime = new Date(now.getTime() + ttl);

        // 创建 jwt 的 Header信息
        HashMap<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ","JWT");
        headerClaims.put("alg","HS256");

        //创建jwt
        String sign = JWT.create()
                .withHeader(headerClaims)  // 头部信息
                .withIssuedAt(now)         // 签发日期
                .withExpiresAt(expireTime) // 过期时间
                .withIssuer("fzdkx")    // 签发人
                .withClaim("userInfo", userInfo)   // 使用声明 ，用户信息
                .withClaim("authList", authList.toString())   // 私有声明 ，权限信息
                .sign(Algorithm.HMAC256(secureKey));     // 签名
        return sign;

    }

    /**
     * 对Token进行验证
     */
    public boolean verifyToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secureKey)).build();
        try {
            // 如果验证不报错，代表验证通过
            DecodedJWT verify = jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            //验证报错，代表验证不通过
            log.error("验签失败：{}", token);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过 token 获取用户信息
     */
    public String getUserInfo(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secureKey)).build();
        try {
            // 验签
            DecodedJWT verify = jwtVerifier.verify(token);
            // 不报错，则返回
            return verify.getClaim("userInfo").asString();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        // 验签失败返回 null
        return null;
    }

    /**
     * 通过 token 获取用户权限信息
     */
    public List<String> getUserAuth(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secureKey)).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            return verify.getClaim("authList").asList(String.class);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
