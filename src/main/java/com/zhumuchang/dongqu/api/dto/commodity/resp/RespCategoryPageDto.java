package com.zhumuchang.dongqu.api.dto.commodity.resp;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author sx
 * @Description 品类分页列表返回参数
 * @Date 2022/7/6 14:13
 */
@Data
public class RespCategoryPageDto implements Serializable {
    private static final long serialVersionUID = 2757403323624285154L;

    /**
     * 对外ID
     */
    private String openId;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类状态 0.停用 1.启用
     */
    private Integer categoryEnable;

    /**
     * 品类创建人
     */
    private String createdName;

    /**
     * 品类创建时间
     */
    private LocalDateTime createdTime;
}
