package com.fzdkx.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.utils.UserThreadLocal;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fzdkx.constant.RedisConstant.REDIS_USER_TOKEN_PRE;

/**
 * @author 发着呆看星
 * @create 2023/8/21 14:25
 */
@Component
public class UserTokenInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private StringRedisTemplate template;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 获取Token
        String token = request.getHeader(jwtProperties.getUserTokenName());
        // 判断是否为空
        if (token == null) {
            response.setStatus(401);
            return false;
        }
        // 校验token
        DecodedJWT verify;
        try {
            verify = jwtUtil.verify(token);
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
        // 判断是否已安全退出登录
        Long id = Long.valueOf(verify.getClaim("id").asString());
        String redisToken = template.opsForValue().get(REDIS_USER_TOKEN_PRE + id);
        if (redisToken == null || redisToken.equals("")) {
            response.setStatus(401);
            return false;
        }
        // 向ThreadLocal中存储用户
        UserThreadLocal.setId(id);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
