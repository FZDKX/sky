package com.fzdkx.controller.admin;

import com.fzdkx.constant.JwtConstant;
import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.Result;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 发着呆看星
 * @create 2023/8/9 15:30
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class EmployeeController {
    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private EmployeeService employeeService;
    @PostMapping("/employee/login")
    public Result<EmployeeLoginVO> employeeLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        // 根据用户名和密码从数据库中查询用户
        Employee employee = employeeService.queryEmployee(employeeLoginDTO);
        // 不报错，代表用户登录成功，为用户生成 jwt令牌
        Map<String, Object> map = new HashMap<>();
        map.put(JwtConstant.EMP_ID,employee.getId());
        // 生成JWT令牌
        String token = JwtUtil.createJWT(jwtProperties.getSecureKey(),
                jwtProperties.getTtl(), map);
        // 封装 EmployeeLoginVO对象
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .username(employee.getUsername())
                .token(token)
                .build();
        return Result.success(employeeLoginVO);
    }
}
