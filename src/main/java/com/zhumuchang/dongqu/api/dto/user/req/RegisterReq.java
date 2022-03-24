package com.zhumuchang.dongqu.api.dto.user.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author sx
 * @Description 注册dto
 * @Date 2022/3/22 18:27
 */
@Data
public class RegisterReq implements Serializable {
    private static final long serialVersionUID = 5984654548570604597L;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    @Length(min = 1, max = 10, message = "请输入最多10为的用户名")
    private String userName;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "请输入正确的手机号")
    private String phone;

    /**
     * 验证码
     */
    private String code;
}
