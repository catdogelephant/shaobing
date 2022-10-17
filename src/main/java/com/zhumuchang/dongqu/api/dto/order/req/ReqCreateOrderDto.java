package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author sx
 * @Description 创建订单请求参数
 * @Date 2022/8/24 17:58
 */
@Data
public class ReqCreateOrderDto implements Serializable {
    private static final long serialVersionUID = 1309467505678925866L;

    /**
     * 收货地址对外ID
     */
    @NotBlank(message = "收货地址为空，请稍后重试")
    private String addressOpenId;

    /**
     * 规格集合
     */
    @NotEmpty(message = "商品信息为空，请稍后重试")
    private List<ReqCreateOrderSpeDto> speList;

    /**
     * 支付方式 0.支付宝 1.微信 2.余额
     */
//    @NotNull(message = "请选择支付方式")
//    @Max(value = 2, message = "支付方式错误， 请稍后重试")
//    @Min(value = 0, message = "支付方式错误， 请稍后重试")
//    private Integer payType;
}
