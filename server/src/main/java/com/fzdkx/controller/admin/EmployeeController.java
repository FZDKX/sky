package com.fzdkx.controller.admin;

import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.result.Result;
import com.fzdkx.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author 发着呆看星
 * @create 2023/8/9 15:30
 */
@RestController
@RequestMapping("/admin")
@Api("员工接口相关方法")
@Slf4j
public class EmployeeController {
    @Resource

    private EmployeeService employeeService;

    @ApiOperation("添加员工")
    @PostMapping("/employee")
    public Result saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }
}
