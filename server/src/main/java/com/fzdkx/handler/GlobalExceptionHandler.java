package com.fzdkx.handler;

import com.fzdkx.exception.BaseException;
import com.fzdkx.exception.TokenNotFoundException;
import com.fzdkx.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 发着呆看星
 * @create 2023/8/11 17:01
 * 全局异常处理器，处理业务移除
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException e){
        log.error("异常信息：{}",e.getMessage());
        return Result.error(e.getMessage());
    }
}
