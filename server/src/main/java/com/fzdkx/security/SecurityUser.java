package com.fzdkx.security;

import com.fzdkx.constant.SqlConstant;
import com.fzdkx.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author 发着呆看星
 * @create 2023/8/10 21:57
 */
public class SecurityUser implements UserDetails {
    private final Employee employee;

    public SecurityUser(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        String password = employee.getPassword();
        employee.setPassword("");
        return password;
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return employee.getStatus() == SqlConstant.DEFAULT_STATUS;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId(){
        return employee.getId();
    }
}
