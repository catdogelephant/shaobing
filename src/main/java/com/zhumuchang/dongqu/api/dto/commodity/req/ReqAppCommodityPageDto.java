package com.zhumuchang.dongqu.api.dto.commodity.req;

import com.zhumuchang.dongqu.api.bean.PageBean;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 商品分页列表请求参数
 * @Date 2022/7/16 16:42
 */
@Data
public class ReqAppCommodityPageDto extends PageBean implements Serializable {
    private static final long serialVersionUID = -9141398416532217821L;

    /**
     * 店铺对外ID
     */
    private String shopOpenId;

    /**
     * 品类对外ID
     */
    private String categoryOpenID;
}
