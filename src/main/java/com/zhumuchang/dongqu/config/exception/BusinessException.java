package com.zhumuchang.dongqu.config.exception;

import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author sx
 * @Description 业务异常类
 * @Date 2022/7/11 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1983285424958590601L;
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
