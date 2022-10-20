package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author sx
 * @Description 订单详情返回参数
 * @Date 2022/10/19 14:35
 */
@Data
public class RespOrderDetailDto implements Serializable {
    private static final long serialVersionUID = -5978912162812631175L;

    /**
     * 店铺对外ID
     */
    private String shopOpenId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品集合
     */
    private List<RespOrderDetailCommodityDto> commodityList;

    /**
     * 订单实付价格
     */
    private BigDecimal payPrice;

    /**
     * 订单原价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态 0.待支付 1.待发货 2.待收货 3.待评价 4.已完成 5.已取消 6.已退款 7.退换中 8.申诉中 9.订单支付超时取消订单
     */
    private Integer orderStatus;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
     */
    private String consigneePhone;

    /**
     * 收货地址
     */
    private String consigneeAddress;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 三方交易号
     */
    private String transactionNo;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 成交时间
     */
    private LocalDateTime TransactionTime;
}
