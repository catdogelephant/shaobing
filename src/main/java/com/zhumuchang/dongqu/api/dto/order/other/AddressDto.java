package com.zhumuchang.dongqu.api.dto.order.other;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收货地址
 * </p>
 *
 * @author sx
 * @since 2022-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDto implements Serializable {

    private static final long serialVersionUID = -2175596563256571757L;

    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 会员id
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
     */
    private String consigneePhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 是否默认 0.否 1.是
     */
    private Integer isDefault;

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
