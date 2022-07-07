package com.zhumuchang.dongqu.commons.aspect;

import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author sx
 * @Description 控制器切面 pjp.proceed(pjp.getArgs());14
 * @Date 2022/7/6 18:29
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Around("execution(* com..*Controller.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.info("控制器切面");
        Object resp = pjp.proceed(pjp.getArgs());
        if (Objects.isNull(resp)) {
            resp = "操作成功";
        }
        return ResultDto.success(resp);
    }
}
