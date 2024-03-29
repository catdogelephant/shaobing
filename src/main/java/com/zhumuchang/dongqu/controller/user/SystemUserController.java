package com.zhumuchang.dongqu.controller.user;


import com.alibaba.fastjson.JSONObject;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.user.SystemUserService;
import com.zhumuchang.dongqu.commons.annotation.ApiIdempotent;
import com.zhumuchang.dongqu.commons.annotation.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
@RestController
@RequestMapping("/user/system")
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @ApiIdempotent
    @PassToken
    @PostMapping(name = "登录", path = "/login")
    public Object login(@Valid @RequestBody LoginDto param, HttpServletRequest request) {
        LoginTokenDto loginTokenDto = systemUserService.login(param);
        return loginTokenDto;
    }
    //异常情况：
    //1、未知：返回500
    //2、可返回错误信息：101

    @ApiIdempotent
    @PassToken
    @PostMapping(name = "注册", path = "/register")
    public ResultDto register(@Valid @RequestBody RegisterReq param) {
        String errorMsg = systemUserService.register(param);
        if (StringUtils.isEmpty(errorMsg)) {
            return new ResultDto(BusinessEnum.SUCCESS, null);
        }
        return new ResultDto(null, BusinessEnum.FAIL.getCode(), errorMsg);
    }

    @PassToken
    @PostMapping(name = "刷新token", path = "/refreshToken")
    public LoginTokenDto refreshToken(@RequestBody LoginDto param) {
        LoginTokenDto resp = new LoginTokenDto();
        if (null == param || StringUtils.isEmpty(param.getPassword()) || StringUtils.isEmpty(param.getAccount())) {
            log.info("刷新token - 参数为空 - param={}", JSONObject.toJSON(param));
            resp.setRespMsg("参数为空");
            return resp;
        }
        resp = systemUserService.login(param);
        return resp;
    }

    @PassToken
    @PostMapping(name = "测试feign调用", path = "/testFeign")
    public LoginDto testFeign() {
        LoginDto loginDto = new LoginDto();
        loginDto.setAccount("woshishabi");
        return loginDto;
    }

    @PassToken
    @GetMapping(name = "测试Security", path = "/testSecurity")
    public ResultDto testSecurity() {
        System.out.println("----------------->>>testSecurity<<<-----------------");
        return new ResultDto(BusinessEnum.SUCCESS, null);
    }

    /*public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(5);
        arrayList.add(2);
//        arrayList.add(1);
//        arrayList.add(3);
        arrayList.add(4);
        Collections.sort(arrayList);
        System.out.println(arrayList);
        int i = Collections.binarySearch(arrayList, 3);
        System.out.println(i);
        arrayList.add(-i-1, 3);
        System.out.println(arrayList);
        Collections.fill(arrayList, 10);
        System.out.println(arrayList);
        Integer[] array = arrayList.toArray(new Integer[1]);
        for (Integer integer : array) {
            System.out.println(integer);
        }
    }*/

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.interrupt();
        thread.stop();
    }

}
