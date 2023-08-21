package com.fzdkx.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzdkx.constant.EmployeeLogin;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.utils.EmployeeThreadLocal;
import com.fzdkx.utils.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fzdkx.constant.RedisConstant.REDIS_TOKEN_PRE;

/**
 * @author 发着呆看星
 * @create 2023/8/21 11:37
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

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
        String token = request.getHeader(jwtProperties.getTokenName());
        // 判断是否为空
        if (token == null) {
            response.setStatus(401, "token为携带");
            return false;
        }
        // 校验token
        DecodedJWT verify;
        try {
            verify = jwtUtil.verify(token);
        } catch (Exception e) {
            response.setStatus(401, "token校验失败");
            return false;
        }
        // 判断是否已安全退出登录
        Long id = Long.valueOf(verify.getClaim("id").asString());
        String redisToken = template.opsForValue().get(REDIS_TOKEN_PRE + id);
        if (redisToken == null || redisToken.equals("")) {
            response.setStatus(401, "Token已失效，请重写登录");
            return false;
        }
        // 向ThreadLocal中存储用户
        String name = verify.getClaim("name").asString();
        String role = verify.getClaim("role").asString();
        EmployeeLogin employeeLogin = new EmployeeLogin(id, name, role);
        EmployeeThreadLocal.set(employeeLogin);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        EmployeeThreadLocal.remove();
    }
}
