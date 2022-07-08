package com.zhumuchang.dongqu.api.dto.commodity.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author sx
 * @Description 新增商品请求参数
 * @Date 2022/7/7 17:07
 */
@Data
public class ReqAddCommodityDto implements Serializable {
    private static final long serialVersionUID = 4017479354249504760L;

    /**
     * 店铺对外ID
     */
    @NotBlank(message = "店铺信息错误")
    private String shopId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称为空")
    @Length(min = 1, max = 10, message = "商品名称最多10位")
    private String commodityName;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格为空")
    private BigDecimal commodityPrice;

    /**
     * 商品介绍
     */
    @NotBlank(message = "商品介绍为空")
    @Length(min = 1, max = 100, message = "商品名称最多100位")
    private String introduce;

    /**
     * 商品缩略图
     */
    @NotBlank(message = "商品缩略图为空")
    private String thumbnail;

    /**
     * 商品展示图片集合（最多六张）
     */
    @NotNull(message = "商品展示图片集合为空")
    @Size(max = 6, message = "商品展示图片集合最多6张")
    private List<String> pictureList;

    /**
     * 限购数量（为空或者等于0时代表不限制购买数量）
     */
    private Integer limitBuy;

    /**
     * 停启用 0.停用 1.启用
     */
    private Integer enable = 0;
}
