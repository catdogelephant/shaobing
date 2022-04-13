package com.zhumuchang.dongqu.api.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 返回值枚举
 * @Date 2022/3/22 18:36
 */
@Getter
public enum ResponseEnum {

    /** 操作成功 */
    SUCCESS(200, "操作成功"),

    /** 操作失败 */
    FAIL(0, "操作失败"),

    /** 登陆失败 */
    LOGIN_FAIL(102, "用户名或密码错误"),

    /** token 失效 */
    TOKEN_FAIL(103, "你的认证信息已失效，请重新登陆"),

    /** 参数类型错误 **/
    PARAM_NULL_FAIL(401, "参数为空"),

    PARAM_ERROR(402, "参数错误")
    ;

    private final Integer code;

    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
