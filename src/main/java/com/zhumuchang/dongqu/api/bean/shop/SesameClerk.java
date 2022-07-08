package com.zhumuchang.dongqu.api.bean.shop;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 店员
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameClerk implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 店铺主键id
     */
    private Integer sesameShopId;

    /**
     * 会员主键id
     */
    private Integer sesameUserId;

    /**
     * 店员类型 1.店主 2.普通店员
     */
    private Integer type;

    /**
     * 是否上架 0.未上架或下架 1.上架
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
