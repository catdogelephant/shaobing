package com.zhumuchang.dongqu.commons.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 操作类别枚举（记录日志用）
 * @Date 2022/8/17 11:04
 */
@Getter
public enum OperatorType {

    /**
     * 0其它
     */
    OTHER(0, "其他"),

    /**
     * 1后台用户
     */
    SYSTEM(1, "后台用户"),

    /**
     * 2手机端用户
     */
    APP(2, "手机端用户"),
    ;

    private final int code;

    private final String msg;

    OperatorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}