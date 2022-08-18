package com.zhumuchang.dongqu.api.dto.commodity.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description app端获取商品规格列表dto
 * @Date 2022/8/18 14:10
 */
@Data
public class AppSpecificationsListDto implements Serializable {
    private static final long serialVersionUID = -2111007246876822369L;

    /**
     * 商品规格名称
     */
    private String specificationsName;

    /**
     * 商品规格价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private String stock;

    /**
     * 规格缩略图
     */
    private String thumbnail;

    /**
     * 限购数量
     */
    private Integer limitBuy;
}
