package com.zhumuchang.dongqu.controller.local;

import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.local.LocalSystemUserService;
import com.zhumuchang.dongqu.config.annotation.PassToken;
import com.zhumuchang.dongqu.config.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sx
 * @Description 本地数据库测试控制器
 * @Date 2022/7/4 10:12
 */
@RestController
@Slf4j
@RequestMapping("/local")
public class LocalController {

    @Autowired
    private LocalSystemUserService localSystemUserService;

    @PassToken
    @PostMapping("/tran")
    public void tran() {
        localSystemUserService.tran();
    }

    @PassToken
    @PostMapping("/testAspectStr")
    public String testAspectStr(String name) {
        throw new BusinessException(BusinessEnum.FAIL);
//        return name;
    }

    @PassToken
    @PostMapping("/testAspectInt")
    public Integer testAspectInt(Integer name) {
        return name;
    }

    @PassToken
    @PostMapping("/testAspectTwo")
    public String testAspectTwo(String name) {
        return "testAspectTwo";
    }
}