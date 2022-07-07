package com.zhumuchang.dongqu.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sx
 * @version 1.0.0
 * @ClassName PassToken
 * @Description 跳过 token 校验
 * @data 2021/3/6 7:21 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {

}
