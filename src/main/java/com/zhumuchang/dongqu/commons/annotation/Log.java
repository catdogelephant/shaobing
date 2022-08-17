package com.zhumuchang.dongqu.commons.annotation;

import com.zhumuchang.dongqu.commons.enumapi.BusinessType;
import com.zhumuchang.dongqu.commons.enumapi.OperatorType;

import java.lang.annotation.*;

/**
 * @Author sx
 * @Description 日志记录注解
 * @Date 2022/8/17 11:03
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.SYSTEM;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}

