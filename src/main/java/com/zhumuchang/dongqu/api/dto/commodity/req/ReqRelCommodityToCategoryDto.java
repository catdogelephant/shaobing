package com.zhumuchang.dongqu.api.dto.commodity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author sx
 * @Description 设置商品所属品类请求参数
 * @Date 2022/7/10 17:13
 */
@Data
public class ReqRelCommodityToCategoryDto implements Serializable {
    private static final long serialVersionUID = 882204280345354835L;

    /**
     * 商品对外ID
     */
    @NotBlank(message = "商品ID为空")
    private String commodityOpenId;

    /**
     * 品类对外ID
     */
    @NotBlank(message = "品类ID为空")
    private String categoryOpenId;

}
