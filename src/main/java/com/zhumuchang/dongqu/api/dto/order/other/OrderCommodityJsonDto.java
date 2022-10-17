package com.zhumuchang.dongqu.api.dto.order.other;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author sx
 * @Description 订单商品Json实体
 * @Date 2022/8/30 11:30
 */
@Data
public class OrderCommodityJsonDto implements Serializable {
    private static final long serialVersionUID = 8446809460582566207L;

    /**
     * 店铺ID
     */
    private Integer shopId;

    /**
     * 店铺对外ID
     */
    private String shopOpenId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品规格集合
     */
    private List<OrderSpeJsonDto> orderSpeList;

    /**
     * 订单备注
     */
    private String remarks;

}
