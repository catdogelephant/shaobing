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
 *
 * </p>
 *
 * @author sx
 * @since 2022-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 购买会员id
     */
    private Integer userId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 三方交易号
     */
    private String transactionNo;

    /**
     * 支付方式 0.支付宝 1.微信 2.余额
     */
    private Integer payType;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 实付价格
     */
    private BigDecimal payPrice;

    /**
     * 优惠价格（总价-实付价格）
     */
    private BigDecimal favorablePrice;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人电话
     */
    private String consigneePhone;

    /**
     * 省
     */
    private String consigneeProvince;

    /**
     * 市
     */
    private String consigneeCity;

    /**
     * 区
     */
    private String consigneeArea;

    /**
     * 街道
     */
    private String consigneeStreet;

    /**
     * 详细地址
     */
    private String consigneeDetailedAddress;

    /**
     * 收货地址
     */
    private String consigneeAddress;

    /**
     * 商品信息json串
     */
    private String commodityJson;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单状态 0.待支付 1.待发货 2.待收货 3.待评价 4.已完成 5.已取消 6.已退款 7.退换中 8.申诉中
     */
    private Integer status;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

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
