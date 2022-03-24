package com.zhumuchang.dongqu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BatongxianInitializr01Application {

    public static void main(String[] args) {
        SpringApplication.run(BatongxianInitializr01Application.class, args);
        log.info("------------------------>>>>启动成功<<<<------------------------");
    }

}
