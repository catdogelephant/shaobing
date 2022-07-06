package com.zhumuchang.dongqu.commons.exception;

import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author sx
 * @Description 自定义业务异常类
 * @Date 2022/7/6 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -5477456383337058062L;

    /**
     * 错误状态码
     */
    private Integer errorCode;

    /**
     * 错误提示
     */
    private String errorMsg;

    public BusinessException(BusinessEnum businessEnum) {
        this.errorCode = businessEnum.getCode();
        this.errorMsg = businessEnum.getMsg();
    }
}
