package com.zhumuchang.dongqu.api.dto.commodity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 一个请求参数的dto
 * @Date 2022/4/12 19:00
 */
@Data
public class ReqOneParamDto implements Serializable {
    private static final long serialVersionUID = 8730723676606977715L;

    /**
     * 请求参数
     */
    private Object param;
}
