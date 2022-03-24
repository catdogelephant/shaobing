package com.zhumuchang.dongqu.api.dto.user.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author sx
 * @Description 登录dto
 * @Date 2022/3/21 18:39
 */
@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 3072063120234888086L;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
}
