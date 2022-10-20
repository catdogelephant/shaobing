package com.zhumuchang.dongqu.controller.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.dto.order.req.ReqAddAddressDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespAddressDetailDto;
import com.zhumuchang.dongqu.api.service.order.SesameAddressService;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(name = "查看收货地址详情", path = "/getAddressDetail")
    public Object getAddressDetail(HttpServletRequest request, @RequestParam String addressOpenId) {
        if (StringUtils.isBlank(addressOpenId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        RespAddressDetailDto resp = sesameAddressService.getAddressDetail(tokenUser, addressOpenId);
        return resp;
    }

    @GetMapping(name = "查询收货地址列表", path = "/pageAddress")
    public Object listAddress(HttpServletRequest request) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        Page<RespAddressDetailDto> resp = sesameAddressService.pageAddress(tokenUser);
        return resp;
    }

}
