package com.zhumuchang.dongqu.controller.user;


import cn.hutool.core.util.IdUtil;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import com.zhumuchang.dongqu.api.enumapi.ResponseEnum;
import com.zhumuchang.dongqu.api.service.SystemUserService;
import com.zhumuchang.dongqu.config.annotation.PassToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/user/system")
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @PassToken
    @PostMapping(name = "登录", path = "/login")
    public ResultDto login(@Valid @RequestBody LoginDto param, HttpServletRequest request) {
        LoginTokenDto resp = systemUserService.login(param);
        if (null == resp) {
            return new ResultDto(null, 500, "登录失败");
        }
        if (!StringUtils.isEmpty(resp.getRespMsg())) {
            return new ResultDto(null, 500, resp.getRespMsg());
        }
        return new ResultDto(resp, 200, "请求成功");
    }
    //异常情况：
    //1、未知：返回500
    //2、可返回错误信息：101

    @PassToken
    @PostMapping(name = "注册", path = "/register")
    public ResultDto register(@Valid @RequestBody RegisterReq param) {
        String errorMsg = systemUserService.register(param);
        if (StringUtils.isEmpty(errorMsg)) {
            return new ResultDto(ResponseEnum.SUCCESS, null);
        }
        return new ResultDto(null, ResponseEnum.FAIL.getCode(), errorMsg);
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
    }

}
