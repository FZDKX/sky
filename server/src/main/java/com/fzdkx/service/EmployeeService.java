package com.fzdkx.service;

import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.entity.Employee;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:15
 */
public interface EmployeeService {
    Employee queryEmployee(EmployeeLoginDTO employeeLoginDTO);

}
