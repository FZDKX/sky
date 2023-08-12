package com.fzdkx.service.impl;

import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.InsertException;
import com.fzdkx.mapper.EmployeeMapper;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.utils.IdThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.fzdkx.constant.MessageConstant.SQL_INSERT_ERROR;
import static com.fzdkx.constant.SqlConstant.DEFAULT_PASSWORD;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:16
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .status(DEFAULT_STATUS)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(IdThreadLocal.getId())
                .updateUser(IdThreadLocal.getId()).build();
        BeanUtils.copyProperties(employeeDTO,employee);
        try {
            employeeMapper.insertEmployee(employee);
        } catch (Exception e) {
            throw new InsertException(SQL_INSERT_ERROR);
        }
    }
}
