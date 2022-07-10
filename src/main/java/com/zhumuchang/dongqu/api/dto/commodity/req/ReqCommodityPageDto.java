package com.zhumuchang.dongqu.api.dto.commodity.req;

import com.zhumuchang.dongqu.api.bean.PageBean;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author sx
 * @Description 商品分页列表请求参数
 * @Date 2022/7/9 19:53
 */
@Data
public class ReqCommodityPageDto extends PageBean implements Serializable {
    private static final long serialVersionUID = -8080286150222666943L;

    /**
     * 店铺对外ID
     */
    private String shopOpenId;

    /**
     * 品类对外ID
     */
    private String categoryOpenId;
}
