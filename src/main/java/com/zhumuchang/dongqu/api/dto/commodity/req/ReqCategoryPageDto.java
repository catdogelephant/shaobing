package com.zhumuchang.dongqu.api.dto.commodity.req;

import com.zhumuchang.dongqu.api.bean.PageBean;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author sx
 * @Description 品类分页列表请求参数
 * @Date 2022/7/6 11:48
 */
@Data
public class ReqCategoryPageDto extends PageBean implements Serializable {
    private static final long serialVersionUID = 3436969365442453052L;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类状态 0.停用 1.启用
     */
    private Integer categoryEnable;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
