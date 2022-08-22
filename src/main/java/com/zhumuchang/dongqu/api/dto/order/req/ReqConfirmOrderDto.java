package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 确认订单页请求参数
 * @Date 2022/8/22 17:50
 */
@Data
public class ReqConfirmOrderDto implements Serializable {
    private static final long serialVersionUID = -5053187216242348756L;

    /**
     * 规格外对ID
     */
    private String specificationsOpenId;

    /**
     * 商品数量
     */
    private Integer num;
}
