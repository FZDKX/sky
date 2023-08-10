package com.fzdkx.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.constant.MessageConstant;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2023/8/10 21:26
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    private ObjectMapper objectMapper;

    public static final String USER_NAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    public LoginFilter() {
        super(new AntPathRequestMatcher("/admin/employee/login" , "POST"));
    }

    // 对客户端传入的登录请求进行封装
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 判断响应格式是否为JSON
        UsernamePasswordAuthenticationToken authRequest;
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
            || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            // 如果是，这则解析JSON串，封装成Map
            Map<String, String> map = new HashMap<>();
            try {
                map = objectMapper.readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String username = map.get(USER_NAME_KEY);
            if (!StringUtils.hasLength(username)){
                throw new UsernameNotFoundException(MessageConstant.USER_NOT_FOUND);
            }
            String password = map.get(PASSWORD_KEY);
            if (!StringUtils.hasLength(password)){
                throw new UsernameNotFoundException(MessageConstant.PASSWORD_ERROR);
            }
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            throw new AuthenticationServiceException(MessageConstant.LAYOUT_ERROR);
        }
    }
}
