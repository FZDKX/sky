package com.fzdkx;

import com.fzdkx.utils.IdThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 发着呆看星
 * @create 2023/8/11 16:41
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IdThreadLocal.removeId();
    }
}
