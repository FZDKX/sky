package com.fzdkx.exception;

/**
 * @author 发着呆看星
 * @create 2023/8/10 23:26
 */
public class TokenErrorException extends BaseException{
    public TokenErrorException() {
    }

    public TokenErrorException(String msg) {
        super(msg);
    }
}
