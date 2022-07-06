package com.zhumuchang.dongqu.api.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 分页基类
 * @Date 2022/7/6 11:45
 */
@Data
public class PageBean implements Serializable {
    private static final long serialVersionUID = 1335471654668309193L;

    /**
     * 行数
     */
    public long size = 10L;

    /**
     * 页码
     */
    public long current = 1L;
}
