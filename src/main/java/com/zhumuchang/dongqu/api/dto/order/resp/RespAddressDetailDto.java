package com.zhumuchang.dongqu.api.dto.order.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 收货地址详情返回参数
 * @Date 2022/8/23 17:23
 */
@Data
public class RespAddressDetailDto implements Serializable {
    private static final long serialVersionUID = -926081059057246079L;

    /**
     * 收货地址对外ID
     */
    private String addressOpenId;

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
     * 是否默认
     */
    private Integer isDefault;
}
