package com.zhumuchang.dongqu.api.dto.order.other;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 订单规格Json实体
 * @Date 2022/8/30 11:37
 */
@Data
public class OrderSpeJsonDto implements Serializable {
    private static final long serialVersionUID = 5667946499509200730L;

    /**
     * 商品ID
     */
    private Integer commodityId;

    /**
     * 商品对外ID
     */
    private String commodityOpenId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private Integer commodityNum;

    /**
     * 规格ID
     */
    private Integer specificationsId;

    /**
     * 规格对外ID
     */
    private String specificationsOpenId;

    /**
     * 规格名称
     */
    private String specificationsName;

    /**
     * 规格价格
     */
    private BigDecimal specificationsPrice;

    /**
     * 规格缩略图
     */
    private String specificationsThumbnail;

    /**
     * 序号
     */
    private Integer sort;
}
