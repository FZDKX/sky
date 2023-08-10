package com.fzdkx.config;

import com.fzdkx.security.LoginFilter;
import com.fzdkx.security.SecurityHandler;
import com.fzdkx.security.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/10 21:15
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration{
    @Resource
    private TokenFilter tokenFilter;

    @Resource
    private SecurityHandler securityHandler;

    // 加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // url配置
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ,LoginFilter loginFilter) throws Exception {
        // 向过滤器链中添加自定义登录过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenFilter, LoginFilter.class);

        http.formLogin().disable();

        // 禁止跨域请求保护 及 session
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 授权请求，允许登录请求
        http.authorizeRequests()
                .antMatchers("/admin/employee/login").permitAll()
                .anyRequest().authenticated();
        // 配置过滤器与处理器
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager){
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(securityHandler::loginSuccess);
        loginFilter.setAuthenticationFailureHandler(securityHandler::loginFail);
        return loginFilter;
    }
}