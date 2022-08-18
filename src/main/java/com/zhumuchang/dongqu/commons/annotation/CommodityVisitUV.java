package com.zhumuchang.dongqu.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author sx
 * @Description 商品访问量UV统计
 * @Date 2022/8/17 14:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommodityVisitUV {
}
