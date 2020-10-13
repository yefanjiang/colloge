package com.college.servicebase.exceptionHandler;

import com.college.commonutils.UnifiedResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public UnifiedResult error(Exception e) {
        e.printStackTrace();
        return UnifiedResult.error().message("执行了全局异常处理");
    }

    /**
     *
     * @param e
     * @return
     * 由于是自定义的异常，因此需要自己抛出
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public UnifiedResult error(MyException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return UnifiedResult.error().code(e.getCode()).message(e.getMsg());
    }
}
