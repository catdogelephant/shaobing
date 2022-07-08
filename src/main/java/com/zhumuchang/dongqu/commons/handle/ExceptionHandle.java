package com.zhumuchang.dongqu.commons.handle;

import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author sx
 * @Description 异常拦截器
 * @Date 2022/3/21 19:51
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDto methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        List<ObjectError> objectErrorList = e.getBindingResult().getAllErrors();
        String errorMsg = objectErrorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(ConstantsUtils.COMMA));
        ResultDto result = new ResultDto(null, 500, errorMsg);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResultDto arrayIndexOutOfBoundsExceptionHandle(ArrayIndexOutOfBoundsException e) {
        String message = e.getMessage();
        ResultDto result = new ResultDto(message, 500, message);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResultDto businessExceptionHandler(BusinessException e) {
        log.info("自定义异常");
        log.error(e.getErrorMsg(), e);
        return ResultDto.businessError(e);
    }
}
