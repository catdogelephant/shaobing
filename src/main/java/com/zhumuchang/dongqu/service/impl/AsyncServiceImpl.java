package com.zhumuchang.dongqu.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author sx
 * @Description 异步操作类
 * @Date 2022/8/6 15:31
 */
@Slf4j
@Service
public class AsyncServiceImpl {

    @Async
    public void testAsyncMethod() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String threadName = Thread.currentThread().getName();
        log.info("异步操作类 - 打印线程名 - threadName={}", threadName);
    }
}
