package com.fzdkx.mapper;

import com.fzdkx.entity.User;

/**
 * @author 发着呆看星
 * @create 2023/8/21 13:42
 */
public interface UserMapper {
    User selectUserByOpenid(String openId);

    void insert(User user);
}
