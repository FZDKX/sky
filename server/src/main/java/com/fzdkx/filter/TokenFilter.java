package com.fzdkx.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzdkx.exception.TokenErrorException;
import com.fzdkx.exception.TokenNotFoundException;
import com.fzdkx.handler.SecurityHandler;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.Result;
import com.fzdkx.security.SecurityUser;
import com.fzdkx.utils.IdThreadLocal;
import com.fzdkx.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
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
@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private SecurityHandler securityHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 过滤请求URI
        if (requestURI.equals("/admin/employee/login")
            || requestURI.contains("/doc.html")
            || requestURI.contains("/webjars/")
            || requestURI.contains("/swagger")
            || requestURI.contains("/favicon")
            || requestURI.contains("/v2")) {
            filterChain.doFilter(request, response);
            return;
        }
        response.setContentType("application/json;charset=utf-8");
        // 获取请求头中的token
        String token = request.getHeader(jwtProperties.getTokenName());
        // token空判断
        if (!StringUtils.hasLength(token)) {
            log.error(TOKEN_NOT_FOUNT);
            response.setStatus(401);
            securityHandler.write(response.getWriter(),Result.error("登录过期，请重新登录"));
            return;
        }
        DecodedJWT verify;
        try {
            // 验签
            verify = jwtUtil.verify(token);
        } catch (Exception e) {
            log.error(TOKEN_VERIFY_ERROR);
            response.setStatus(401);
            securityHandler.write(response.getWriter(),Result.error("登录过期，请重新登录"));
            return;
        }
        long id = Long.parseLong(verify.getClaim(TOKEN_ID).asString()) ;

        // redis验证
        String redisToken = template.opsForValue().get(REDIS_TOKEN_PRE + id);
        if (!StringUtils.hasLength(redisToken)) {
            log.error(TOKEN_EXPIRE_ERROR);
            response.setStatus(401);
            securityHandler.write(response.getWriter(),Result.error("登录过期，请重新登录"));
            return;
        }
        if (!redisToken.equals(token)) {
            log.error(TOKEN_VERIFY_ERROR);
            response.setStatus(401);
            securityHandler.write(response.getWriter(),Result.error("登录过期，请重新登录"));
            return;
        }
        IdThreadLocal.setId(id);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
