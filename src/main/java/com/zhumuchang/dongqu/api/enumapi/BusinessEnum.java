package com.zhumuchang.dongqu.api.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 返回值枚举
 * @Date 2022/3/22 18:36
 */
@Getter
public enum BusinessEnum {

    /** 操作成功 */
    SUCCESS(200, "操作成功"),

    /** 操作失败 */
    FAIL(0, "操作失败"),

    /** 登陆失败 */
    LOGIN_FAIL(10002, "用户名或密码错误"),

    /** token 失效 */
    TOKEN_FAIL(10003, "你的认证信息已失效，请重新登陆"),

    /** 操作类型错误 */
    NO_PERMISSION(10004, "没有权限操作"),

    /** 操作类型错误 */
    NO_TOKEN(10005, "用户信息为空"),

    /** 参数类型错误 */
    PARAM_NULL_FAIL(10011, "参数为空"),

    PARAM_ERROR(10012, "参数错误"),

    DATA_NOT_FOUND(10013, "数据不存在"),

    DATA_BUSINESS_ERROR(10015, "数据业务异常"),

    DATA_ERROR(10016, "实体数据异常")
    ;

    private final Integer code;

    private final String msg;

    BusinessEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
