package com.zhumuchang.dongqu.controller.commodity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.bean.PageBean;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespAppCategoryPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.page.StringPageDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCategoryService;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * app端品类 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/app/commodity")
public class SesameCommodityAppController {

    /**
     * 品类
     */
    @Autowired
    private SesameCategoryService sesameCategoryService;

    /**
     * 商品
     */
    @Autowired
    private SesameCommodityService sesameCommodityService;

    @GetMapping(name = "品类分页列表", value = "/appCategoryPage")
    public Object appCategoryPage(PageBean param) {
        Page<RespAppCategoryPageDto> resp = sesameCategoryService.appCategoryPage(param);
        return resp;
    }

    @GetMapping(name = "获取商品分页列表", path = "/appCommodityPage")
    public Object appCommodityPage(StringPageDto param) {
        Page<RespCommodityPageDto> resp = sesameCommodityService.appCommodityPage(param);
        return resp;
    }
}
