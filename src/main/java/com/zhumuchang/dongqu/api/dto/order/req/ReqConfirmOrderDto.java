package com.zhumuchang.dongqu.api.dto.order.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author sx
 * @Description 确认订单页请求参数
 * @Date 2022/8/22 17:50
 */
@Data
public class ReqConfirmOrderDto implements Serializable {
    private static final long serialVersionUID = -5053187216242348756L;

    /**
     * 店铺对外ID
     */
    private String shopOpenId;

    /**
     * 规格对外ID集合
     */
    private List<String> speOpenIdList;
}
