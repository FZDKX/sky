package com.fzdkx.service.impl;

import com.fzdkx.dto.EditEmployeePasswordDTO;
import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.PasswordErrorException;
import com.fzdkx.mapper.EmployeeMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.EmployeeService;
import com.fzdkx.utils.IdThreadLocal;
import com.fzdkx.vo.EmployeeEditVO;
import com.fzdkx.vo.EmployeePageQueryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.fzdkx.constant.MessageConstant.SQL_EMPLOYEE_PASSWORD_ERROR;
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
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .status(DEFAULT_STATUS)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD)).build();
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
        Long id = IdThreadLocal.getId();
        String encodePassword = employeeMapper.selectEmployeePasswordById(id);
        boolean flag = passwordEncoder.matches(employeePasswordDTO.getOldPassword(), encodePassword);
        if (!flag) {
            throw new PasswordErrorException(SQL_EMPLOYEE_PASSWORD_ERROR);
        }
        String newEncodePassword = passwordEncoder.encode(employeePasswordDTO.getNewPassword());
        Employee employee = Employee.builder()
                .id(id)
                .password(newEncodePassword).build();
        BeanUtils.copyProperties(employeePasswordDTO, employee);
        employeeMapper.updateEmployee(employee);
    }

}
