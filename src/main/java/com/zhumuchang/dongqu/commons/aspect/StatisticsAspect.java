package com.zhumuchang.dongqu.commons.aspect;

import com.zhumuchang.dongqu.commons.utils.RedisUtils;
import com.zhumuchang.dongqu.commons.utils.RequestLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author sx
 * @Description 统计切面
 * @Date 2022/8/17 14:38
 */
@Slf4j
@Aspect
@Component
public class StatisticsAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(* com.zhumuchang.dongqu.controller.commodity.SesameCommodityAppController.appCommodityDetail(..))")
    public void commodityVisitUVPointcut() {
    }


    @Before("commodityVisitUVPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //获取userId
        String tokenUserId = RequestLocal.getTokenUserId();
        if (StringUtils.isBlank(tokenUserId)) {
            return;
        }
        for (Object arg : args) {
            if (Objects.isNull(arg)) {
                continue;
            }
            if (arg instanceof String) {
                String commodityOpenId = (String) arg;
                if (StringUtils.isBlank(commodityOpenId)) {
                    continue;
                }
                //保存到redis中
                String key = RedisUtils.getKey(RedisUtils.COMMODITY_VISIT_UV_KEY, commodityOpenId);
                stringRedisTemplate.opsForHyperLogLog().add(key, tokenUserId);
            }
        }
    }
}
