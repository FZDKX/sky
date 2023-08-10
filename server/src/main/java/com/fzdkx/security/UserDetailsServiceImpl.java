package com.fzdkx.security;

import com.fzdkx.constant.MessageConstant;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.UserNotFoundException;
import com.fzdkx.mapper.EmployeeMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/10 23:06
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private EmployeeMapper employeeMapper;
    // 根据用户名 从数据库中查找用户，封装成UserDetails对象
    // 用于和LoginFilter中接收的前端传入的 用户名和密码进行比较
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeMapper.getEmployee(username);
        // 账户为空
        if (employee == null){
            throw new UserNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        // 封装成UserDetails对象返回
        return new SecurityUser(employee);
    }
}
