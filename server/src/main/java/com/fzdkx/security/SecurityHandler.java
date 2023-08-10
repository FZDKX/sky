package com.fzdkx.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.Result;
import com.fzdkx.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.fzdkx.constant.MessageConstant.*;
import static com.fzdkx.constant.RedisConstant.REDIS_TOKEN_PRE;

/**
 * @author 发着呆看星
 * @create 2023/8/10 21:41
 * 自定义安全处理器
 */
@Slf4j
@Component
public class SecurityHandler {
    @Resource
    private StringRedisTemplate template;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private JwtUtil jwtUtil;

    // 认证成功处理器
    public void loginSuccess(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication authentication)
            throws IOException {
        // 设置响应格式及编码格式
        response.setContentType("application/json;charset=utf-8");
        // 通过authentication 得到 UserDetails对象
        SecurityUser securityUser =(SecurityUser) authentication.getPrincipal();
        // 通过 userDetails对象得到 用户信息 ,并转成json
        String userJson = objectMapper.writeValueAsString(securityUser.getEmployee());
        // 调用JWT工具类方法 ，将用户信息和权限信息传入，得到Token
        String token = jwtUtil.createToken(userJson,new ArrayList<>());
        // 将Token添加到Redis中 ，Key中携带Token ，value为用户名
        template.opsForValue().set(REDIS_TOKEN_PRE+securityUser.getUsername(), token,2, TimeUnit.HOURS);
        // 将Token转为Json数据
        String json = objectMapper.writeValueAsString(Result.success(token));
        PrintWriter out = response.getWriter();
        // 返回json数据
        out.write(json);
        // 刷新
        out.flush();
        // 关闭流
        out.close();
    }

    // 认证失败处理器
    public void loginFail(HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException exception)
            throws IOException {
        // 设置响应格式 及其 编码
        response.setContentType("application/json;charset=utf-8");
        Result<String> result = null;
        // 根据错误异常，填写响应对象的错误信息
        if(exception instanceof UsernameNotFoundException){
            result = Result.error(USER_NOT_FOUND);
        }else if (exception instanceof AuthenticationServiceException){
            result = Result.error(AUTHENTICATION_ERROR);
        }
        // 将对象转为json
        String json = objectMapper.writeValueAsString(result);
        PrintWriter out = response.getWriter();
        // 返回json数据
        out.write(json);
        // 刷新
        out.flush();
        // 关闭流
        out.close();
    }



    // 安全退出成功处理器
    public void logoutSuccess(HttpServletRequest request,
                              HttpServletResponse response,
                              Authentication authentication)
            throws IOException, ServletException {
        // 设置 响应格式 及其 编码
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 获取用户携带的Token
        String token = request.getHeader(jwtProperties.getTokenName());
        String json;
        if (StringUtils.hasLength(token)){
            json = objectMapper.writeValueAsString(Result.error(LOGOUT_ERROR));
        }else {
            // 删除Redis中的Token
            template.delete("token:"+token);
            // 获取响应对象的json格式数据
            json = objectMapper.writeValueAsString(Result.success("退出成功"));
        }
        // 返回json数据
        out.write(json);
        // 刷新
        out.flush();
        // 关闭流
        out.close();
    }
}
