package com.zhumuchang.dongqu.api.dto.commodity.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 商品分页列表返回参数
 * @Date 2022/7/9 19:57
 */
@Data
public class RespCommodityPageDto implements Serializable {
    private static final long serialVersionUID = 7923527432613091971L;

    /**
     * 商品对外ID
     */
    private String commodityOpenId;

    /**
     * 商品缩略图
     */
    private String thumbnail;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品价格
     */
    private BigDecimal price;
}
