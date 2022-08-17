package com.zhumuchang.dongqu.commons.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 业务状态枚举（记录日志用）
 * @Date 2022/8/17 11:05
 */
@Getter
public enum BusinessStatus {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    FAIL(0, "失败")
    ;

    private final int code;

    private final String msg;

    BusinessStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
