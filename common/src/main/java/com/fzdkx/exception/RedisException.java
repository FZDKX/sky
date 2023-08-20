package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/20 19:42
 */
public class RedisException extends BaseException{
    public RedisException() {
    }

    public RedisException(String msg) {
        super(msg);
    }
}
