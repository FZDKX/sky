package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/12 9:51
 */
public class SqlException extends RuntimeException{
    public SqlException() {
    }

    public SqlException(String message) {
        super(message);
    }
}
