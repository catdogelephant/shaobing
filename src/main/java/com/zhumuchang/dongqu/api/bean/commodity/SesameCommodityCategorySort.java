package com.zhumuchang.dongqu.api.bean.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品品类排序表
 * </p>
 *
 * @author sx
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameCommodityCategorySort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺主键id
     */
    private Integer sesameShopId;

    /**
     * 商品主键id
     */
    private Integer sesameCommodityId;

    /**
     * 品类id
     */
    private Integer sesameCategoryId;

    /**
     * 序号
     */
    private Integer sort;

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
    private String updatedId;

    /**
     * 更新人
     */
    private String updatedName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
