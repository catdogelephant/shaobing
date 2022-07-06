package com.zhumuchang.dongqu.service.impl.test;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author sx
 * @Description 抽象类
 * @Date 2022/6/6 16:49
 */
@Component
public abstract class TestAbstract {

    private static final Map<String, TestAbstract> oauthBeanMap = new HashMap<>();

    protected abstract String getOauthType();

    @PostConstruct
    private void register() {
        System.out.println("----- init -----");
//        System.out.println("----- 开始 -----" + getOauthType());
//        oauthBeanMap.put(getOauthType(), this);
//        System.out.println("----- 结束 -----" + getOauthType());
    }
}
