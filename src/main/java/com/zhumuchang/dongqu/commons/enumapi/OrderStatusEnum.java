package com.zhumuchang.dongqu.commons.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 订单状态枚举
 * @Date 2022/8/29 19:48
 */
@Getter
public enum OrderStatusEnum {

    /**
     * 未支付
     */
    TO_BE_PAID(0, "待支付"),

    /**
     * 待发货
     */
    TO_BE_SHIPPED(1, "待发货"),

    /**
     * 待收货
     */
    TO_BE_RECEIVED(2, "待收货"),

    /**
     * 待评价
     */
    TO_BE_EVALUATED(3, "待评价"),

    /**
     * 已完成
     */
    FINISH(4, "已完成"),

    /**
     * 已取消
     */
    CANCELLED(5, "已取消"),

    /**
     * 已退款
     */
    REFUNDED(6, "已退款"),

    /**
     * 退换中
     */
    RETURNING_AND_REPLACING(7, "退换中"),

    /**
     * 申诉中
     */
    APPEAL(8, "申诉中");

    private final Integer code;

    private final String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
