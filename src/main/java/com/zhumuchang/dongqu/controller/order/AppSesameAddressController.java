package com.zhumuchang.dongqu.controller.order;


import com.zhumuchang.dongqu.api.dto.order.req.ReqAddAddressDto;
import com.zhumuchang.dongqu.api.service.order.SesameAddressService;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 收货地址 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-08-23
 */
@RestController
@RequestMapping("/order/address")
public class AppSesameAddressController {

    @Autowired
    private SesameAddressService sesameAddressService;

    @PostMapping(name = "添加收货地址", path = "/addAddress")
    public Object addAddress(HttpServletRequest request, @Valid @RequestBody ReqAddAddressDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameAddressService.addAddress(tokenUser, param);
        return null;
    }

}
