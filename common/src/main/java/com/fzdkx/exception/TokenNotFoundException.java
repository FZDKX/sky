package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/11 22:39
 */
public class TokenNotFoundException extends BaseException{
    public TokenNotFoundException() {
    }

    public TokenNotFoundException(String msg) {
        super(msg);
    }
}
