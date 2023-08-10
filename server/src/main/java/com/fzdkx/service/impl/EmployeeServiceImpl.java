package com.fzdkx.service.impl;

import com.fzdkx.constant.MessageConstant;
import com.fzdkx.constant.StatusConstant;
import com.fzdkx.exception.PasswordErrorException;
import com.fzdkx.exception.UserNotFoundException;
import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.UserStatusLockException;
import com.fzdkx.mapper.EmployeeMapper;
import com.fzdkx.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:16
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee queryEmployee(EmployeeLoginDTO employeeLoginDTO) {
        // 根据用户名查询用户
        Employee employee = employeeMapper.getEmployee(employeeLoginDTO.getUsername());
        if (employee == null){
            throw new UserNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        // 如果账户存在，就比对密码是否正确
        // 对前端的明文密码进行MD5加密
        String password = DigestUtils.md5DigestAsHex(employeeLoginDTO.getPassword().getBytes());
        if (!employee.getPassword().equals(password)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROE);
        }
        // 如果账号密码都正确，那么对账户进行锁定判断
        if (employee.getStatus() == StatusConstant.USER_LOCK){
            throw new UserStatusLockException(MessageConstant.USER_STATUS_LOCK);
        }
        return employee;
    }
}
