package com.fzdkx.mapper;

import com.fzdkx.entity.User;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/21 13:42
 */
public interface UserMapper {
    User selectUserByOpenid(String openId);

    void insert(User user);

    User getById(Long userId);

    Integer selectTotalUser(@Param("end") LocalDateTime endTime);

    Integer selectNewUser(@Param("begin") LocalDateTime begin,@Param("end") LocalDateTime end);
}
