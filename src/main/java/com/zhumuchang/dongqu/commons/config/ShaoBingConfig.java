package com.zhumuchang.dongqu.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author sx
 * @Description 自定义配置类
 * @Date 2022/8/5 15:12
 */
@Configuration
public class ShaoBingConfig {

    /**
     * 线程池的配置
     */
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setKeepAliveSeconds(300);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadNamePrefix("shaobing_");
        taskExecutor.setDaemon(false);
        return taskExecutor;
    }
}
