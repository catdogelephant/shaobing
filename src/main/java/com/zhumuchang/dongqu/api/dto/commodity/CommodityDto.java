package com.zhumuchang.dongqu.api.dto.commodity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 商品数据dto
 * @Date 2022/7/8 14:29
 */
@Data
public class CommodityDto implements Serializable {

    private static final long serialVersionUID = -2857344731282626975L;
    /**
     * id
     */
    private Integer id;

    /**
     * 店铺主键id
     */
    private Integer sesameShopId;

    /**
     * 品类主键id
     */
    private Integer sesameCategoryId;

    /**
     * 停启用 0.停用 1.启用
     */
    private Integer enable;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品名称
     */
    private String commodityName;
}
