package com.fzdkx.service.impl;

import com.fzdkx.dto.EmployeeDTO;
import com.fzdkx.dto.EmployeePageQueryDTO;
import com.fzdkx.entity.Employee;
import com.fzdkx.exception.InsertException;
import com.fzdkx.exception.SqlException;
import com.fzdkx.mapper.EmployeeMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
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
import java.time.LocalDateTime;
import java.util.List;

import static com.fzdkx.constant.MessageConstant.SQL_INSERT_ERROR;
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
    public Result addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .status(DEFAULT_STATUS)
                .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(IdThreadLocal.getId())
                .updateUser(IdThreadLocal.getId()).build();
        BeanUtils.copyProperties(employeeDTO, employee);
        try {
            employeeMapper.insertEmployee(employee);
        } catch (Exception e) {
            throw new InsertException(SQL_INSERT_ERROR);
        }
        return Result.success();
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
    public Result changeStatus(Integer status, Integer id) {
        try {
            employeeMapper.updateEmployeeStatusById(status, id);
        } catch (RuntimeException e) {
            throw new SqlException("sql执行异常");
        }
        return Result.success();
    }

    @Override
    public void editEmployeeInfo(Employee employee) {
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(IdThreadLocal.getId());
        employeeMapper.updateEmployeeInfo(employee);

    }

    @Override
    public EmployeeEditVO queryEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }

}
