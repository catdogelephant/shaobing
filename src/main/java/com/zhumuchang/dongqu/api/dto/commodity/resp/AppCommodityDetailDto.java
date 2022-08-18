package com.zhumuchang.dongqu.api.dto.commodity.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author sx
 * @Description app获取商品详情dto
 * @Date 2022/8/17 18:14
 */
@Data
public class AppCommodityDetailDto implements Serializable {
    private static final long serialVersionUID = -8689094049068191119L;

    /**
     * 商品对外ID
     */
    private String commodityOpenId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品介绍
     */
    private String introduce;

    /**
     * 商品展示图片集合（最多六张）
     */
    private String pictureJson;

    private List<String> pictureList;

    /**
     * 销量
     */
    private Integer sale;
}
