package com.zhumuchang.dongqu.api.dto.user;

import com.zhumuchang.dongqu.api.enumapi.ResponseEnum;
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
    private Object resp;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    public ResultDto(ResponseEnum responseEnum, Object resp) {
        this.resp = resp;
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }

    public ResultDto(){}

    public ResultDto(Object resp, Integer code, String msg) {
        this.resp = resp;
        this.code = code;
        this.msg = msg;
    }
}
