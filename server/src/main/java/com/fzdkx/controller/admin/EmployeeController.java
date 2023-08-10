package com.fzdkx.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.Result;
import com.fzdkx.security.SecurityUser;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SystemUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author 发着呆看星
 * @create 2023/8/9 15:30
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class EmployeeController {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtil jwtUtil;

//    @PostMapping("/employee/login")
//    public Result<EmployeeLoginVO> employeeLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO) throws JsonProcessingException {
//        String username = employeeLoginDTO.getUsername();
//        String password = employeeLoginDTO.getPassword();
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        SecurityUser securityUser =(SecurityUser) authenticate.getPrincipal();
//        Employee employee = securityUser.getEmployee();
//        // 通过 userDetails对象得到 用户信息 ,并转成json
//        String userJson = objectMapper.writeValueAsString(securityUser.getEmployee());
//        // 调用JWT工具类方法 ，将用户信息和权限信息传入，得到Token
//        String token = jwtUtil.createToken(userJson,new ArrayList<>());
//        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
//                .name(employee.getName())
//                .username(employee.getUsername())
//                .id(employee.getId())
//                .token(token).build();
//        return Result.success(employeeLoginVO);
//    }
}
