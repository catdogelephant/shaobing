package com.zhumuchang.dongqu.api.dto.user;

import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 统一返回值
 * @Date 2022/3/21 18:53
 */
@Data
public class ResultDto implements Serializable {
    private static final long serialVersionUID = -6987999520409138967L;

    /**
     * 返回值
     */
    private Object data;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    public ResultDto(BusinessEnum businessEnum, Object data) {
        this.data = data;
        this.code = businessEnum.getCode();
        this.msg = businessEnum.getMsg();
    }

    public ResultDto() {
    }

    public ResultDto(Object data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 业务异常返回结果
     *
     * @param be 业务异常
     * @return 返回结果
     */
    public static ResultDto businessError(BusinessException be) {
        return new ResultDto(null, be.getErrorCode(), be.getErrorMsg());
    }

    /**
     * 其他异常返回结果
     *
     * @param businessEnum 返回枚举
     * @return 返回结果
     */
    public static ResultDto otherError(BusinessEnum businessEnum) {
        return new ResultDto(null, businessEnum.getCode(), businessEnum.getMsg());
    }

    public static ResultDto success() {
        return new ResultDto(BusinessEnum.SUCCESS, null);
    }

    public static ResultDto success(Object data) {
        return new ResultDto(BusinessEnum.SUCCESS, data);
    }

    public static ResultDto fail() {
        return new ResultDto(BusinessEnum.FAIL, null);
    }

}
