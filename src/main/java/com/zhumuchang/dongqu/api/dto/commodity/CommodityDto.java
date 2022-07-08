package com.zhumuchang.dongqu.api.dto.commodity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 商品数据dto
 * @Date 2022/7/8 14:29
 */
@Data
public class CommodityDto implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 店铺主键id
     */
    private String sesameShopId;

    /**
     * 品类主键id
     */
    private Integer sesameCategoryId;

    /**
     * 停启用 0.停用 1.启用
     */
    private Integer enable;
}
