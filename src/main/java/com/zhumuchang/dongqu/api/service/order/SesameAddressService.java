package com.zhumuchang.dongqu.api.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.order.SesameAddress;
import com.zhumuchang.dongqu.api.dto.order.req.ReqAddAddressDto;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

/**
 * <p>
 * 收货地址 服务类
 * </p>
 *
 * @author sx
 * @since 2022-08-23
 */
public interface SesameAddressService extends IService<SesameAddress> {

    /**
     * 添加收货地址
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    void addAddress(TokenUser tokenUser, ReqAddAddressDto param);
}
