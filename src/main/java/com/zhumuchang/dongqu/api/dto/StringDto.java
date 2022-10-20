package com.zhumuchang.dongqu.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 单字符串实体
 * @Date 2022/10/19 14:31
 */
@Data
public class StringDto implements Serializable {
    private static final long serialVersionUID = -1918238234481409174L;

    private String strParam;
}
