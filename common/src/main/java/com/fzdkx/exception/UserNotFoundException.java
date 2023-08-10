package com.fzdkx.exception;

import com.fzdkx.exception.BaseException;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:25
 * 账户未找到异常
 */
public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {

    }

    public UserNotFoundException(String msg){
        super(msg);
    }

}
