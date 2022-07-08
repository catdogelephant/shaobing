package com.zhumuchang.dongqu.api.bean.shop;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 店铺
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SesameShop implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 停启用 0.停用 1.启用（默认停用）
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
