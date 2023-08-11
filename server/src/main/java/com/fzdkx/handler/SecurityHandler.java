package com.fzdkx.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.entity.Employee;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.Result;
import com.fzdkx.security.SecurityUser;
import com.fzdkx.utils.IdThreadLocal;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.fzdkx.constant.JwtConstant.TOKEN_ID;
import static com.fzdkx.constant.JwtConstant.TOKEN_USERNAME;
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

        // 封装 jwt map
        Employee employee = securityUser.getEmployee();
        Map<String, String> map = new HashMap<>();
        map.put(TOKEN_USERNAME, employee.getUsername());
        map.put(TOKEN_ID, employee.getId().toString());

        // 得到Token
        String token = jwtUtil.createToken(map);

        // 将Token添加到Redis中
        template.opsForValue().set(REDIS_TOKEN_PRE+securityUser.getId(),
                                    token,jwtProperties.getTtl(), TimeUnit.HOURS);

        // 封装响应对象
        EmployeeLoginVO employeeLoginVO = new EmployeeLoginVO();
        BeanUtils.copyProperties(employee, employeeLoginVO);
        employeeLoginVO.setToken(token);

        // 将employeeLoginVO响应给前端
        write(response.getWriter(),Result.success(employeeLoginVO));
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

        // 响应
        write(response.getWriter(),result);
    }



    // 安全退出成功处理器
    public void logoutSuccess(HttpServletRequest request,
                              HttpServletResponse response,
                              Authentication authentication)
            throws IOException, ServletException {
        // 设置 响应格式 及其 编码
        response.setContentType("application/json;charset=utf-8");

        // 获取用户携带的Token
        String token = request.getHeader(jwtProperties.getTokenName());

        // 判断
        Result result;
        if (!StringUtils.hasLength(token)){
            result = Result.error(LOGOUT_ERROR);
        }else {
            // 删除Redis中的Token
            template.delete("token:" + IdThreadLocal.getId());
            // 获取响应对象的json格式数据
            result = Result.success("退出成功");
        }
        write(response.getWriter(),result);
    }

    private void write(Writer out , Result result) throws IOException {
        String json = objectMapper.writeValueAsString(result);
        // 返回json数据
        out.write(json);
        // 刷新
        out.flush();
        // 关闭流
        out.close();
    }
}
