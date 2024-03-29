package com.zhumuchang.dongqu.controller.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCartDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqConfirmOrderDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCreateOrderDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespConfirmOrderDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderDetailDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderPageDto;
import com.zhumuchang.dongqu.api.dto.page.IntegerPageDto;
import com.zhumuchang.dongqu.api.dto.page.StringPageDto;
import com.zhumuchang.dongqu.api.service.order.SesameOrderService;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-08-19
 */
@RestController
@RequestMapping("/order")
public class AppSesameOrderController {

    @Autowired
    private SesameOrderService sesameOrderService;

    @PostMapping(name = "添加购物车", path = "/addCart")
    public Object addCart(HttpServletRequest request, @RequestBody ReqCartDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameOrderService.addCart(tokenUser, param);
        return null;
    }

    @GetMapping(name = "获取购物车列表", path = "/getCart")
    public Object getCart(HttpServletRequest request) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        List<RespCartDto> resp = sesameOrderService.getCart(tokenUser);
        return resp;
    }

    @PostMapping(name = "清空购物车", path = "/delCart")
    public Object delCart(HttpServletRequest request) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameOrderService.delCart(tokenUser);
        return null;
    }

    @PostMapping(name = "确认订单页", path = "/confirmOrder")
    public Object confirmOrder(HttpServletRequest request, @RequestBody List<ReqConfirmOrderDto> param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        RespConfirmOrderDto resp = sesameOrderService.confirmOrder(tokenUser, param);
        return resp;
    }

    @PostMapping(name = "创建订单", path = "/createOrder")
    public Object createOrder(HttpServletRequest request, @Valid @RequestBody ReqCreateOrderDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameOrderService.createOrder(tokenUser, param);
        //TODO 这里应该要返回支付接口需要的参数，目前不做处理
        return null;
    }

    @PostMapping(name = "获取订单分页列表", path = "/getOrderPage")
    public Object getOrderPage(HttpServletRequest request, @RequestBody IntegerPageDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        Page<RespOrderPageDto> resp = sesameOrderService.getOrderPage(tokenUser, param);
        return resp;
    }

    @PostMapping(name = "获取订单详情", path = "/getOrderDetail")
    public Object getOrderDetail(HttpServletRequest request, @RequestBody StringPageDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        RespOrderDetailDto resp = sesameOrderService.getOrderDetail(tokenUser, param);
        return resp;
    }

    @PostMapping(name = "删除订单", path = "/delOrder")
    public Object delOrder(HttpServletRequest request, @RequestBody StringPageDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameOrderService.delOrder(tokenUser, param);
        return null;
    }

    @PostMapping(name = "取消订单", path = "/cancelOrder")
    public Object cancelOrder(HttpServletRequest request, @RequestBody StringPageDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameOrderService.cancelOrder(tokenUser, param);
        return null;
    }

}
