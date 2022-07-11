package com.zhumuchang.dongqu.config.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author sx
 * @Description 测试切面
 * @Date 2022/7/11 15:54
 */
@Slf4j
@Aspect
@Component
public class TestAspect {
    //流程提交时
    private static final String TEST_CONTROLLER =
            "execution(* com.zhumuchang.dongqu.controller.local.LocalController.testAspectStr(..))"
                    + " || " +
                    "execution(* com.zhumuchang.dongqu.controller.local.LocalController.testAspectInt(..))";


    @AfterReturning(pointcut = TEST_CONTROLLER, returning="rtRt")
    public void testAspectAfter(JoinPoint joinPoint, String rtRt){
        log.info("----------------------》》》 分割线 <<<----------------------------");
        log.info("rt={}", JSONObject.toJSONString(rtRt));
    }

    @AfterReturning(pointcut = TEST_CONTROLLER, returning="rtRt")
    public void testAspectAfterTwo(JoinPoint joinPoint, Integer rtRt){
        log.info("----------------------》》》 分割线 <<<----------------------------");
        log.info("rt={}", JSONObject.toJSONString(rtRt));
    }
}
