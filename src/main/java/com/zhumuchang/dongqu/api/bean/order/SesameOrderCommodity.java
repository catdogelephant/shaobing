package com.zhumuchang.dongqu.api.bean.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author sx
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameOrderCommodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 订单id
     */
    private Integer sesameOrderId;

    /**
     * 订单编号
     */
    private String sesameOrderNo;

    /**
     * 店铺id
     */
    private Integer sesameShopId;

    /**
     * 店铺对外id
     */
    private String sesameShopOpenId;

    /**
     * 店铺名称
     */
    private String sesameShopName;

    /**
     * 商品ID
     */
    private Integer sesameCommodityId;

    /**
     * 商品对外ID
     */
    private String sesameCommodityOpenId;

    /**
     * 商品名称
     */
    private String sesameCommodityName;

    /**
     * 规格ID
     */
    private Integer sesameSpecificationsId;

    /**
     * 规格对外ID
     */
    private String sesameSpecificationsOpenId;

    /**
     * 规格名称
     */
    private String sesameSpecificationsName;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 实付价格
     */
    private BigDecimal payPrice;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品状态：1.正常 2.退款成功
     */
    private Integer status;

    /**
     * 删除状态 0.删除 1.正常
     */
    private Integer delFlag;

    /**
     * 创建人id
     */
    private Integer createdId;

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
    private Integer updatedId;

    /**
     * 更新人
     */
    private String updatedName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
