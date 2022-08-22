package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 确认订单页-商品集合
 * @Date 2022/8/22 18:24
 */
@Data
public class ConfirmOrderCommodityListDto implements Serializable {
    private static final long serialVersionUID = -6827837215494534805L;

    /**
     * 商品缩略图
     */
    private String commodityThumbnail;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品原价（商品上的标价，不展示优惠后的）
     */
    private BigDecimal commodityPrice;

    /**
     * 商品数量（前端传）
     */
    private Integer commodityNum;

    /**
     * 规格名称
     */
    private String specificationsName;

    /**
     * 规格ID
     */
    private String specificationsOpenId;
}
