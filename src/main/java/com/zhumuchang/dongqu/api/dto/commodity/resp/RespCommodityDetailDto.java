package com.zhumuchang.dongqu.api.dto.commodity.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author sx
 * @Description 商品详情返回参数
 * @Date 2022/7/7 14:09
 */
@Data
public class RespCommodityDetailDto implements Serializable {
    private static final long serialVersionUID = 9032745135028137248L;

    /**
     * 对外ID
     */
    private String openId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品价格
     */
    private BigDecimal commodityPrice;

    /**
     * 商品介绍
     */
    private String introduce;

    /**
     * 商品缩略图
     */
    private String thumbnail;

    /**
     * 商品展示图片集合（最多六张）
     */
    private String pictureJson;

    private List<String> pictureList;

    /**
     * 限购数量（为空或者等于0时代表不限制购买数量）
     */
    private Integer limitBuy;

    /**
     * 创建人姓名
     */
    private String createdName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
