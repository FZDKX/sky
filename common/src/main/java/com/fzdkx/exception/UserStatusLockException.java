package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:43
 * 用户状态锁定异常
 */
public class UserStatusLockException extends BaseException{
    public UserStatusLockException() {
    }

    public UserStatusLockException(String msg){
        super(msg);
    }
}
