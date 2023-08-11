package com.fzdkx.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.TokenErrorException;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.utils.IdThreadLocal;
import com.fzdkx.utils.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fzdkx.constant.JwtConstant.TOKEN_ID;
import static com.fzdkx.constant.MessageConstant.*;
import static com.fzdkx.constant.RedisConstant.REDIS_TOKEN_PRE;

/**
 * @author 发着呆看星
 * @create 2023/8/10 23:16
 * 对Token进行校验
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StringRedisTemplate template;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // 过滤请求URI
        if (request.getRequestURI().equals("/admin/employee/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 获取请求头中的token
        String token = request.getHeader(jwtProperties.getTokenName());
        // token空判断
        if (!StringUtils.hasLength(token)) {
            throw new AuthenticationException(AUTHENTICATION_ERROR);
        }
        DecodedJWT verify;
        try {
            // 验签
            verify = jwtUtil.verify(token);
        } catch (Exception e) {
            throw new TokenErrorException(TOKEN_VERIFY_ERROR);
        }
        long id = Long.parseLong(verify.getClaim(TOKEN_ID).toString()) ;

        // redis验证
        String redisToken = template.opsForValue().get(REDIS_TOKEN_PRE + id);
        if (!StringUtils.hasLength(redisToken)) {
            throw new TokenErrorException(TOKEN_EXPIRE_ERROR);
        }
        if (redisToken.equals(token)) {
            throw new TokenErrorException(TOKEN_VERIFY_ERROR);
        }
        IdThreadLocal.setId(id);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
