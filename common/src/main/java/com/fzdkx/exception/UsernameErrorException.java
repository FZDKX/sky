package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/10 23:11
 */
public class UsernameErrorException extends BaseException{
    public UsernameErrorException() {
    }

    public UsernameErrorException(String msg) {
        super(msg);
    }
}
