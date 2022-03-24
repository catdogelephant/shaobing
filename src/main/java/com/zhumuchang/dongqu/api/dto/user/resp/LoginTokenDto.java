package com.zhumuchang.dongqu.api.dto.user.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 登录成功返回token信息
 * @Date 2022/3/21 18:51
 */
@Data
public class LoginTokenDto implements Serializable {
    private static final long serialVersionUID = -7136304381229478453L;

    /**
     * token
     */
    private String token;

    /**
     * userId
     */
    private String userId;
}
