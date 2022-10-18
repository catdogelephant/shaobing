package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 订单列表返回参数-商品集合
 * @Date 2022/10/17 18:43
 */
@Data
public class RespOrderPageCommodityDto implements Serializable {
    private static final long serialVersionUID = -8137536307192074260L;

    private String orderCommodityId;

    /**
     * 商品对外id
     */
    private String commodityOpenId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 规格对外id
     */
    private String specificationsOpenId;

    /**
     * 规格名称
     */
    private String specificationsName;

    /**
     * 规格缩略图
     */
    private String specificationsThumbnail;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 规格实付价格
     */
    private BigDecimal spePayPrice;

    /**
     * 商品状态 1.正常 2.退款成功
     */
    private Integer status;

}
