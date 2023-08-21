package com.fzdkx.service;

import com.fzdkx.vo.UserLoginVO;

/**
 * @author 发着呆看星
 * @create 2023/8/21 13:01
 */
public interface UserService {
    UserLoginVO wxLogin(String code);

    void wxLogout();
}
