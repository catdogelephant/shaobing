package com.zhumuchang.dongqu.controller.commodity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqRelCommodityToCategoryDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @PostMapping(name = "新增商品", value = "/addCommodity")
    public Object addCommodity(HttpServletRequest request, @Valid @RequestBody ReqAddCommodityDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommodityService.addCommodity(param, tokenUser);
        return null;
    }

    @PostMapping(name = "停启用商品", path = "/enableCommodity")
    public Object enableCommodity(HttpServletRequest request, String commodityId) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommodityService.enableCommodity(commodityId, tokenUser);
        return null;
    }

    @PostMapping(name = "删除商品", path = "/delCommodity")
    public Object delCommodity(HttpServletRequest request, String commodityId) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommodityService.delCommodity(commodityId, tokenUser);
        return null;
    }

    @PostMapping(name = "商品分页列表", path = "/commodityPage")
    public Object commodityPage(HttpServletRequest request, @RequestBody ReqCommodityPageDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        Page<RespCommodityPageDto> resp = sesameCommodityService.commodityPage(tokenUser, param);
        return resp;
    }

    @PostMapping(name = "设置商品所属的品类", path = "/relCommodityToCategory")
    public Object relCommodityToCategory(HttpServletRequest request, @Valid @RequestBody ReqRelCommodityToCategoryDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommodityService.relCommodityToCategory(tokenUser, param);
        return null;
    }
}
