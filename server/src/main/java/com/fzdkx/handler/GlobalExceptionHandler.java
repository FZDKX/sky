package com.fzdkx.handler;

import com.fzdkx.exception.TokenNotFoundException;
import com.fzdkx.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 发着呆看星
 * @create 2023/8/11 17:01
 * 全局异常处理器，处理业务移除
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public Result exceptionHandler(TokenNotFoundException e){
        log.error("异常信息：{}",e.getMessage());
        return Result.error(e.getMessage());
    }
}
