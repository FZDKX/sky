package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/26 22:42
 */
public class OrderBusinessException extends BaseException{
    public OrderBusinessException() {
    }

    public OrderBusinessException(String msg) {
        super(msg);
    }
}
