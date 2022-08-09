package com.zhumuchang.dongqu.service.feign;

import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author sx
 * @Description 定时任务远程调用接口
 * @Date 2022/4/15 14:26
 */
@FeignClient(name = "sesame-logs", url = "http://localhost:8022")
public interface LogsFeignService {
    //@FeignClient name对应被调用方（即服务提供方）的 spring.application.name

    @ResponseBody
    @GetMapping(value = "/logs/testLogs")
    LoginTokenDto testLogs(@RequestParam String sql, @RequestParam String sqlCommandType, @RequestParam String databaseName,
                           @RequestParam String tokenUserId, @RequestParam String tokenUserName);
}
