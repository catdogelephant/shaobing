package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author sx
 * @Description 购物车商品集合
 * @Date 2022/8/19 15:23
 */
@Data
public class RespCartCommodityListDto implements Serializable {
    private static final long serialVersionUID = 8642434533177960222L;

    /**
     * 商品ID
     */
    private String commodityOpenId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品价格
     */
    private BigDecimal commodityPrice;

    /**
     * 商品数量
     */
    private Integer commodityNum;

    /**
     * 规格ID
     */
    private String specificationsOpenId;

    /**
     * 规格名称
     */
    private String specificationsName;

    /**
     * 商品更新时间
     */
    private LocalDateTime commodityUpdateTime;

    /**
     * 规格缩略图
     */
    private String specificationsThumbnail;
}
