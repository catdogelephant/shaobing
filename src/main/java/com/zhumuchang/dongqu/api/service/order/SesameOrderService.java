package com.zhumuchang.dongqu.api.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCartDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqConfirmOrderDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCreateOrderDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespConfirmOrderDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderDetailDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderPageDto;
import com.zhumuchang.dongqu.api.dto.page.IntegerPageDto;
import com.zhumuchang.dongqu.api.dto.page.StringPageDto;
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

    /**
     * 清空购物车
     *
     * @param tokenUser 用户信息
     */
    void delCart(TokenUser tokenUser);

    /**
     * 确认订单页
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     * @return 订单页商品列表
     */
    RespConfirmOrderDto confirmOrder(TokenUser tokenUser, List<ReqConfirmOrderDto> param);

    /**
     * 创建订单
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    void createOrder(TokenUser tokenUser, ReqCreateOrderDto param);

    /**
     * 获取订单分页列表
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     * @return 订单列表
     */
    Page<RespOrderPageDto> getOrderPage(TokenUser tokenUser, IntegerPageDto param);

    /**
     * 获取订单详情
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     * @return 订单详情
     */
    RespOrderDetailDto getOrderDetail(TokenUser tokenUser, StringPageDto param);

    /**
     * 删除订单
     *
     * @param tokenUser 用户信息
     * @param param     订单openId
     */
    void delOrder(TokenUser tokenUser, StringPageDto param);

    /**
     * 消息队列取消订单
     *
     * @param list 订单id集合
     */
    void queueCancelOrderByList(List<Integer> list);

    /**
     * 取消订单
     *
     * @param tokenUser 用户信息
     * @param param     订单对外id
     */
    void cancelOrder(TokenUser tokenUser, StringPageDto param);
}
