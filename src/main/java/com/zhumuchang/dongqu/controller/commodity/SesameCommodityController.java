package com.zhumuchang.dongqu.controller.commodity;


import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@RestController
@RequestMapping("/commodity/sesame")
public class SesameCommodityController {

    @Autowired
    private SesameCommodityService sesameCommodityService;

    @GetMapping(name = "获取商品详情", path = "/commodityDetail")
    public Object commodityDetail(String id) {
        RespCommodityDetailDto resp = sesameCommodityService.commodityDetail(id);
        return resp;
    }

}
