package com.zhumuchang.dongqu.api.dto.order.other;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 订单商品实体
 * @Date 2022/8/29 18:26
 */
@Data
public class OrderCommodityDto implements Serializable {
    private static final long serialVersionUID = -2983387540908677568L;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 规格名称
     */
    private String speName;

    /**
     * 原价
     */
    private BigDecimal spePrice;

}
