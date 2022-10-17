package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * @Author sx
 * @Description 创建订单请求参数-规格信息
 * @Date 2022/8/24 18:00
 */
@Data
public class ReqCreateOrderSpeDto implements Serializable {
    private static final long serialVersionUID = -484460950795033624L;

    /**
     * 规格数量集合
     */
    private List<ReqCreateOrderSpeNumDto> speNumList;

    /**
     * 备注
     */
    @Length(max = 200)
    private String remarks;
}
