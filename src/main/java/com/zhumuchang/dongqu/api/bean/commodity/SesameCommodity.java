package com.zhumuchang.dongqu.api.bean.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameCommodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 店铺主键id
     */
    private String sesameShopId;

    /**
     * 品类主键id
     */
    private Integer sesameCategoryId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品介绍
     */
    private String introduce;

    /**
     * 商品缩略图
     */
    private String thumbnail;

    /**
     * 商品图片集合
     */
    private String pictureJson;

    /**
     * 商品限购数量（为空或者等于0时代表不限制购买数量）
     */
    private Integer limitBuy;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 停启用 0.停用 1.启用
     */
    private Integer enable;

    /**
     * 删除状态 0.删除 1.正常
     */
    private Integer delFlag;

    /**
     * 创建人id
     */
    private String createdId;

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
    private String updateId;

    /**
     * 更新人
     */
    private String updatedName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
