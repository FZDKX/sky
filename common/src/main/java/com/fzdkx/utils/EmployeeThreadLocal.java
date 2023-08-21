package com.fzdkx.utils;

import com.fzdkx.constant.EmployeeLogin;

/**
 * @author 发着呆看星
 * @create 2023/8/21 11:23
 */
public class EmployeeThreadLocal {
    private static final ThreadLocal<EmployeeLogin> threadLocal = new ThreadLocal<>();

    private static EmployeeLogin employeeLogin;

    public static Long getId(){
        if (employeeLogin == null){
            return null;
        }
        return employeeLogin.getId();
    }
    public static String getName(){
        if (employeeLogin == null){
            return null;
        }
        return employeeLogin.getName();
    }
    public static String getRole(){
        if (employeeLogin == null){
            return null;
        }
        return employeeLogin.getRole();
    }

    public static void remove(){
        EmployeeLogin employee = threadLocal.get();
        if (employee != null){
            threadLocal.remove();
            employeeLogin = null;
        }
    }

    public static void set(EmployeeLogin employee){
        threadLocal.set(employee);
        employeeLogin = employee;
    }
}
