package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 购物车请求参数
 * @Date 2022/8/19 16:30
 */
@Data
public class ReqCartDto implements Serializable {
    private static final long serialVersionUID = 2596168735811974178L;

    /**
     * 商品规格对外ID
     */
    private String specificationsOpenId;

    /**
     * 商品数量
     */
    private Integer num;
}
