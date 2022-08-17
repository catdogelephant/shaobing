package com.zhumuchang.dongqu.commons.enumapi;

import lombok.Getter;

/**
 * @Author sx
 * @Description 业务类型枚举（记录日志用）
 * @Date 2022/8/17 11:03
 */
@Getter
public enum BusinessType {

    /**
     * 0其它
     */
    OTHER(0, "其他"),

    /**
     * 1新增
     */
    ADD(1, "新增"),

    /**
     * 2修改
     */
    UPDATE(2, "修改"),

    /**
     * 3删除
     */
    DELETE(3, "删除")
    ;

    private final int code;

    private final String msg;

    BusinessType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
