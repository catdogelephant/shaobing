package com.zhumuchang.dongqu.api.dto.commodity.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 设置商品规格请求参数
 * @Date 2022/7/14 17:53
 */
@Data
public class ReqAddCommoditySpecificationsDto implements Serializable {
    private static final long serialVersionUID = -3550785250495077447L;

    /**
     * 商品对外ID
     */
    private String commodityOpenId;

    /**
     * 商品规格名称
     */
    private String specificationsName;

    /**
     * 价格浮动（比主价格便宜就是负整数，比主价格贵就是正整数）
     */
    private Integer priceFloat;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 规格缩略图
     */
    private String thumbnail;

    /**
     * 商品限购数量
     */
    private Integer limitBuy;

    /**
     * 规格价格
     */
    private BigDecimal price;

    /**
     * 商品主键ID
     */
    private Integer commodityId;
}
