package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author sx
 * @Description 确认订单页-店铺集合
 * @Date 2022/8/22 18:21
 */
@Data
public class ConfirmOrderShopListDto implements Serializable {
    private static final long serialVersionUID = -6827837215494534805L;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺Logo
     */
    private String shopLogo;

    /**
     * 商品集合
     */
    private List<ConfirmOrderCommodityListDto> commodityList;

}
