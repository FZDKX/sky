package com.fzdkx.service.impl;

import com.fzdkx.dto.EditEmployeePasswordDTO;
import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeeLoginDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.PasswordErrorException;
import com.fzdkx.exception.UserNotFoundException;
import com.fzdkx.exception.UserStatusLockException;
import com.fzdkx.mapper.EmployeeMapper;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.utils.EmployeeThreadLocal;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.utils.Md5Utils;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeeLoginVO;
import com.fzdkx.vo.EmployeePageQueryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.fzdkx.constant.MessageConstant.SQL_EMPLOYEE_PASSWORD_ERROR;
import static com.fzdkx.constant.RedisConstant.REDIS_TOKEN_PRE;
import static com.fzdkx.constant.SqlConstant.DEFAULT_PASSWORD;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:16
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private JwtProperties jwtProperties;



    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .status(DEFAULT_STATUS)
                .password(Md5Utils.encode(DEFAULT_PASSWORD)).build();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.insertEmployee(employee);
    }

    @Override
    public PageResult<EmployeePageQueryVO> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // 设置分页信息
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        // 执行查询语句
        List<EmployeePageQueryVO> employees = employeeMapper.pageSelectEmployee(employeePageQueryDTO);
        // 对查询结果进行分页，获取分页信息对象
        PageInfo<EmployeePageQueryVO> employeePageInfo = new PageInfo<>(employees);
        // 取出分页信息
        // 总记录条数
        long total = employeePageInfo.getTotal();
        // 当前页信息
        List<EmployeePageQueryVO> list = employeePageInfo.getList();
        return new PageResult<>(total, list);
    }

    @Override
    public void changeStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public void editEmployeeInfo(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public EmployeeEditVO queryEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }

    @Override
    public void editEmployeePassword(EditEmployeePasswordDTO employeePasswordDTO) {
        Long id = EmployeeThreadLocal.getId();
        if (id == null){
            throw new UserNotFoundException("当前用户为找到");
        }
        String encodePassword = employeeMapper.selectEmployeePasswordById(id);
        String oldPassword = Md5Utils.encode(employeePasswordDTO.getOldPassword());
        if (!oldPassword.equals(encodePassword)) {
            throw new PasswordErrorException(SQL_EMPLOYEE_PASSWORD_ERROR);
        }
        String newEncodePassword = Md5Utils.encode(employeePasswordDTO.getNewPassword());
        Employee employee = Employee.builder()
                .id(id)
                .password(newEncodePassword).build();
        BeanUtils.copyProperties(employeePasswordDTO, employee);
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO) {
        // 根据用户名查询数据库
        Employee employee = employeeMapper.selectEmployeeByName(employeeLoginDTO.getUsername());
        if (employee == null){
            throw new UserNotFoundException("用户还未注册");
        }
        // 对比密码
        String password = Md5Utils.encode(employeeLoginDTO.getPassword());
        if (!password.equals(employee.getPassword())){
            throw new PasswordErrorException("密码错误");
        }
        if (employee.getStatus().equals(0)){
            throw new UserStatusLockException("账户被锁定");
        }
        // 生成Token
        String token = getToken(employee);
        // 将Token存储至Redis
        template.opsForValue().set(REDIS_TOKEN_PRE+employee.getId(),token,jwtProperties.getTtl(), TimeUnit.HOURS);
        // 获取VO对象返回
        EmployeeLoginVO employeeLoginVO = new EmployeeLoginVO();
        BeanUtils.copyProperties(employee,employeeLoginVO);
        employeeLoginVO.setToken(token);
        return employeeLoginVO;
    }

    @Override
    public void logout(){
        template.delete(REDIS_TOKEN_PRE+ EmployeeThreadLocal.getId());
    }

    private String getToken(Employee employee){
        Map<String, String> map = new HashMap<>();
        map.put("id",employee.getId().toString());
        map.put("name",employee.getName());
        map.put("role",employee.getRole());
        return jwtUtil.createToken(map);
    }
}
