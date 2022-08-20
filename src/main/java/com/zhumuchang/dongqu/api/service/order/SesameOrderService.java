package com.zhumuchang.dongqu.api.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCartDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartDto;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sx
 * @since 2022-08-19
 */
public interface SesameOrderService extends IService<SesameOrder> {

    /**
     * 添加购物车
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    void addCart(TokenUser tokenUser, ReqCartDto param);

    /**
     * 获取购物车列表
     *
     * @param tokenUser 用户信息
     * @return 购物车列表
     */
    List<RespCartDto> getCart(TokenUser tokenUser);
}
