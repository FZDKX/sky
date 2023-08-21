package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeePageQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/10 14:45
 */
public interface EmployeeMapper {
    Employee selectEmployeeByName(@Param("username") String username);

    @AutoFill(AutoFillType.INSERT)
    void insertEmployee(Employee employee);

    List<EmployeePageQueryVO> pageSelectEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    EmployeeEditVO selectEmployeeById(Long id);

    String selectEmployeePasswordById(@Param("empId") Long empId);

    @AutoFill(AutoFillType.UPDATE)
    void updateEmployee(Employee employee);
}
