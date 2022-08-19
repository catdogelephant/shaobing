package com.zhumuchang.dongqu.api.dto.commodity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 规格dto
 * @Date 2022/8/19 16:50
 */
@Data
public class SpecificationsDto implements Serializable {
    private static final long serialVersionUID = 842875283748768882L;

    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 店铺主键id
     */
    private Integer sesameShopId;

    /**
     * 店铺对外id
     */
    private String sesameShopOpenId;

    /**
     * 商品主键id
     */
    private Integer sesameCommodityId;

    /**
     * 商品对外id
     */
    private String sesameCommodityOpenId;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格价格
     */
    private BigDecimal price;

    /**
     * 规格缩略图
     */
    private String thumbnail;
}
