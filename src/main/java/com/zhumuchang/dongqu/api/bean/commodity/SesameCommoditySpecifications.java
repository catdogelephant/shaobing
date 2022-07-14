package com.zhumuchang.dongqu.api.bean.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品规格
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameCommoditySpecifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 商品主键id
     */
    private Integer sesameCommodityId;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 价格浮动
     */
    private BigDecimal priceFloat;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 规格缩略图
     */
    private String thumbnail;

    /**
     * 商品限购数量（为空或者等于0时代表不限制购买数量）
     */
    private Integer limitBuy;

    /**
     * 创建人id
     */
    private String createdId;

    /**
     * 创建人
     */
    private String createdName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人id
     */
    private String updatedId;

    /**
     * 更新人
     */
    private String updatedName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
