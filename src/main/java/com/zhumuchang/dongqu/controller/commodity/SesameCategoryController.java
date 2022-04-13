package com.zhumuchang.dongqu.controller.commodity;


import com.zhumuchang.dongqu.api.dto.commodity.req.ReqOneParamDto;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCategoryService;
import com.zhumuchang.dongqu.config.interceptor.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 品类 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/commodity/category")
public class SesameCategoryController {

    @Autowired
    private SesameCategoryService sesameCategoryService;

    @PostMapping(name = "新增品类", value = "/addCategory")
    public ResultDto addCategory(HttpServletRequest request, @RequestBody ReqOneParamDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        String errorMsg = sesameCategoryService.addCategory(param, tokenUser);
        if (!StringUtils.isEmpty(errorMsg)) {
            return new ResultDto(null, 500, errorMsg);
        }
        return new ResultDto(null, 200, "请求成功");
    }
}
