package com.zhumuchang.dongqu.api.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.order.SesameAddress;
import com.zhumuchang.dongqu.api.dto.order.req.ReqAddAddressDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespAddressDetailDto;
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

    /**
     * 获取收货地址详情
     *
     * @param tokenUser     用户信息
     * @param addressOpenId 收货地址对外ID
     * @return 收货地址详情
     */
    RespAddressDetailDto getAddressDetail(TokenUser tokenUser, String addressOpenId);

    /**
     * 查询收货地址列表
     *
     * @param tokenUser 用户信息
     * @return 收货地址列表
     */
    Page<RespAddressDetailDto> pageAddress(TokenUser tokenUser);
}
