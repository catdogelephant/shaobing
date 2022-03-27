package com.zhumuchang.dongqu.config.aspect;

import com.zhumuchang.dongqu.config.annotation.ApiIdempotent;
import com.zhumuchang.dongqu.config.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 幂等切面
 * <br>
 * created date 1.13 11:57
 *
 * @author DongJunHao
 */
@Slf4j
@Aspect
@Component
public class ApiIdempotentAspect {

    /**
     * redis缓存key的模板
     */
    private static final String KEY_TEMPLATE = "idempotent_%s";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Idempotent注解位置
     */
    @Pointcut("@annotation(com.zhumuchang.dongqu.config.annotation.ApiIdempotent)")
    public void executeIdempotent() {
    }

    @Around("executeIdempotent()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取幂等注解
        ApiIdempotent idempotent = method.getAnnotation(ApiIdempotent.class);
        //根据 key前缀 + @Idempotent.value() + 方法签名 + 参数 构建缓存键值
        String generate = KeyUtil.generate(method, joinPoint.getArgs());
        log.info("key:" + generate);
        String key = String.format(KEY_TEMPLATE, idempotent.value() + generate);
        //确保幂等处理的操作对象是：同样的 @Idempotent.value() + 方法签名 + 参数
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            redisTemplate.opsForValue().set(key, "idempotent", 50, TimeUnit.SECONDS);
            return joinPoint.proceed();
        }
        log.info("Idempotent hits, key=" + key);
        return "重复访问！";
//        throw new PrivateCaptureException("重复访问!");
    }
}
