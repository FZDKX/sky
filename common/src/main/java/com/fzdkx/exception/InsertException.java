package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/12 9:52
 */
public class InsertException extends SqlException{
    public InsertException() {
    }

    public InsertException(String message) {
        super(message);
    }
}
