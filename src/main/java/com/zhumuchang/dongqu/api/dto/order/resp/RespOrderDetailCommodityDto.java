package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author sx
 * @Description 订单详情-商品信息
 * @Date 2022/10/19 14:40
 */
@Data
public class RespOrderDetailCommodityDto implements Serializable {
    private static final long serialVersionUID = -6022560431898350502L;

    private String orderCommodityId;

    /**
     * 商品对外id
     */
    private String commodityOpenId;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 规格对外id
     */
    private String specificationsOpenId;

    /**
     * 规格名称
     */
    private String specificationsName;

    /**
     * 规格缩略图
     */
    private String specificationsThumbnail;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 规格实付价格
     */
    private BigDecimal spePayPrice;

    /**
     * 规格原价
     */
    private BigDecimal originalPrice;

    /**
     * 商品状态 1.正常 2.退款成功
     */
    private Integer speStatus;

}
