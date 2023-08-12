package com.fzdkx.mapper;

import com.fzdkx.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2023/8/10 14:45
 */
public interface EmployeeMapper {
    Employee selectEmployeeByName(@Param("username") String username);

    void insertEmployee(Employee employee);
}
