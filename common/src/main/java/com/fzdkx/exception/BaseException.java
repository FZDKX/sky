package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:27
 * 业务异常类
 */
public class BaseException extends RuntimeException{
    public BaseException() {

    }

    public BaseException(String msg){
        super(msg);
    }
}
