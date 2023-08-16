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
    /**
     * 添加员工信息
     */
    void addEmployee(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工信息
     */
    PageResult<EmployeePageQueryVO> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 修改员工状态
     */
    void changeStatus(Integer status, Long id);

    /**
     * 修改员工信息
     */
    void editEmployeeInfo(Employee employee);

    /**
     * 根据ID查询员工信息
     */
    EmployeeEditVO queryEmployeeById(Long id);

    /**
     * 修改员工密码
     */
    void editEmployeePassword(EditEmployeePasswordDTO editEmployeePasswordDTO);

}
