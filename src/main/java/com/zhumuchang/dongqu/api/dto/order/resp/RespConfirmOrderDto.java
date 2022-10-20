package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author sx
 * @Description 确认订单页返回参数
 * @Date 2022/8/22 18:18
 */
@Data
public class RespConfirmOrderDto implements Serializable {
    private static final long serialVersionUID = 4439373943190872543L;

    /**
     * 商品总数量
     */
    private Integer totalNum;

    /**
     * 合计
     */
    private BigDecimal totalPrice;

    /**
     * 实付
     */
    private BigDecimal payPrice;

    /**
     * 优惠价格
     */
    private BigDecimal favorablePrice;

    /**
     * 店铺集合
     */
    private List<RespCartDto> shopList;
}
