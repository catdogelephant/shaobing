package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author sx
 * @Description 订单列表返回参数
 * @Date 2022/10/17 18:42
 */
@Data
public class RespOrderPageDto implements Serializable {
    private static final long serialVersionUID = -2244293103309973363L;

    /**
     * 订单对外id
     */
    private String orderOpenId;

    /**
     * 店铺对外id
     */
    private String shopOpenId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品集合
     */
    private List<RespOrderPageCommodityDto> commodityList;

    /**
     * 订单实付金额
     */
    private BigDecimal payPrice;
}
