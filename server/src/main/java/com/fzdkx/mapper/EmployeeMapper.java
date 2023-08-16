package com.fzdkx.mapper;

import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeePageQueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/10 14:45
 */
public interface EmployeeMapper {
    Employee selectEmployeeByName(@Param("username") String username);

    void insertEmployee(Employee employee);

    List<EmployeePageQueryVO> pageSelectEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    void updateEmployeeStatusById(@Param("status") Integer status,@Param("id") Integer id);

    void updateEmployeeInfo(Employee employee);

    EmployeeEditVO selectEmployeeById(Long id);
}
