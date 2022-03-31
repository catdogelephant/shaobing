package com.zhumuchang.dongqu.config.handle;

import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author sx
 * @Description 异常拦截器
 * @Date 2022/3/21 19:51
 */
@RestControllerAdvice
public class ExceptionHandle {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDto methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        ResultDto result = new ResultDto(e.getBindingResult().getFieldError().getDefaultMessage(), 500, "参数异常");
        return result;
    }

    @ResponseBody
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResultDto arrayIndexOutOfBoundsExceptionHandle(ArrayIndexOutOfBoundsException e) {
        String message = e.getMessage();
        ResultDto result = new ResultDto(message, 500, message);
        return result;
    }
}
