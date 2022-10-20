package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 创建订单请求参数-规格数量信息
 * @Date 2022/10/16 16:38
 */
@Data
public class ReqCreateOrderSpeNumDto implements Serializable {
    private static final long serialVersionUID = -7983789797168237131L;

    /**
     * 规格对外ID
     */
    private String specificationsOpenId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 规格序号
     */
    private Integer sort;
}
