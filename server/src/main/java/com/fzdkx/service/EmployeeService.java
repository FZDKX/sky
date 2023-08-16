package com.fzdkx.service;

import com.fzdkx.dto.EditEmployeePasswordDTO;
import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeePageQueryVO;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:15
 */
public interface EmployeeService {

    Result addEmployee(EmployeeDTO employeeDTO);

    PageResult<EmployeePageQueryVO> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    Result changeStatus(Integer status, Integer id);

    void editEmployeeInfo(Employee employee);

    EmployeeEditVO queryEmployeeById(Long id);

    void editEmployeePassword(EditEmployeePasswordDTO editEmployeePasswordDTO);

}
