package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author sx
 * @Description 购物车dto
 * @Date 2022/8/19 15:21
 */
@Data
public class RespCartDto implements Serializable {
    private static final long serialVersionUID = 4287192225567118355L;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 店铺ID
     */
    private String shopOpenId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品集合
     */
    private List<RespCartCommodityListDto> cartCommodityList;
}
