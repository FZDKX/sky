package com.fzdkx.mapper;

import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2023/8/10 14:45
 */
public interface EmployeeMapper {
    Employee getEmployee(@Param("username") String username);
}
