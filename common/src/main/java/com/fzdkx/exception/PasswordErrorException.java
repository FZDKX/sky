package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:35
 * 密码错误异常
 */
public class PasswordErrorException extends BaseException{
    public PasswordErrorException(){

    }

    public PasswordErrorException(String msg){
        super(msg);
    }
}
