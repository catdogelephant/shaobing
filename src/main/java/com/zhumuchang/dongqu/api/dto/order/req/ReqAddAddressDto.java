package com.zhumuchang.dongqu.api.dto.order.req;

import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author sx
 * @Description 新增收货地址实体
 * @Date 2022/8/23 17:23
 */
@Data
public class ReqAddAddressDto implements Serializable {
    private static final long serialVersionUID = -926081059057246079L;

    /**
     * 收货地址对外ID
     */
    private String addressOpenId;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "请输入收货人姓名")
    @Length(max = 10, message = "收货人姓名最多十个字")
    private String consigneeName;

    /**
     * 收货人手机号
     */
    @NotBlank(message = "请输入收货人手机号")
    @Pattern(regexp = ConstantsUtils.REGEX_MOBILE, message = "请输入正确的手机号")
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
    @NotBlank(message = "请输入详细地址")
    @Length(max = 100, message = "详细地址最多100字")
    private String detailedAddress;

    /**
     * 是否默认
     */
    @Max(value = 2, message = "参数错误")
    @Min(value = 1, message = "参数错误")
    private Integer isDefault;
}
