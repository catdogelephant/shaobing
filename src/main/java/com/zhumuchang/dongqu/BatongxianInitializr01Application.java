package com.zhumuchang.dongqu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableEurekaClient
@EnableAspectJAutoProxy
//服务发现
//@EnableDiscoveryClient
@Slf4j
@SpringBootApplication
public class BatongxianInitializr01Application {

    public static void main(String[] args) {
        SpringApplication.run(BatongxianInitializr01Application.class, args);
        log.info("------------------------>>>>启动成功<<<<------------------------");
    }

}
