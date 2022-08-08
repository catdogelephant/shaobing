package com.zhumuchang.dongqu.controller.commodity;


import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommoditySpecificationsDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommoditySpecificationsService;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 商品规格 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
@Slf4j
@RestController
@RequestMapping("/commodity/specifications")
public class SesameCommoditySpecificationsController {

    @Autowired
    private SesameCommoditySpecificationsService sesameCommoditySpecificationsService;

    @PostMapping(name = "设置商品规格", path = "/addCommoditySpecifications")
    public Object addCommoditySpecifications(HttpServletRequest request, @RequestBody ReqAddCommoditySpecificationsDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommoditySpecificationsService.addCommoditySpecifications(tokenUser, param);
        return null;
    }

}
