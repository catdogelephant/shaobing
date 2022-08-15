package com.zhumuchang.dongqu.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author sx
 * @Description 加解密配置类
 * @Date 2022/8/12 17:41
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.encrypt")
//@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptProperties {
    private final static String DEFAULT_KEY = "www.shaobing.com";

    private String key = DEFAULT_KEY;
}
