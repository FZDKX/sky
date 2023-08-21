package com.fzdkx.controller.admin;

import com.fzdkx.dto.EditEmployeePasswordDTO;
import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeeLoginVO;
import com.fzdkx.vo.EmployeePageQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author 发着呆看星
 * @create 2023/8/9 15:30
 */
@RestController
@RequestMapping("/admin/employee")
@Api(tags = "员工接口相关方法")
@Slf4j
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        EmployeeLoginVO employeeLoginVO = employeeService.login(employeeLoginDTO);
        return Result.success(employeeLoginVO);
    }
    @PostMapping("/logout")
    private Result logout(){
        employeeService.logout();
        return Result.success();
    }

    @ApiOperation("添加员工")
    @PostMapping()
    public Result saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    @ApiOperation("员工分页查询")
    @GetMapping("/page")
    public Result<PageResult<EmployeePageQueryVO>> pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult<EmployeePageQueryVO> pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("改变员工账号状态")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        employeeService.changeStatus(status, id);
        return Result.success();
    }

    @ApiOperation("修改员工信息")
    @PutMapping()
    public Result editEmployeeInfo(@RequestBody Employee employee) {
        employeeService.editEmployeeInfo(employee);
        return Result.success();
    }

    @ApiOperation("根据Id查询员工")
    @GetMapping("/{id}")
    public Result<EmployeeEditVO> queryEmployeeById(@PathVariable("id") Long id) {
        EmployeeEditVO employeeEditVO = employeeService.queryEmployeeById(id);
        return Result.success(employeeEditVO);
    }

    @ApiOperation("修改密码")
    @PutMapping("/editPassword")
    public Result editEmployeePassword(@RequestBody EditEmployeePasswordDTO editEmployeePasswordDTO) {
        employeeService.editEmployeePassword(editEmployeePasswordDTO);
        return Result.success();
    }


}
