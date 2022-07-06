package com.zhumuchang.dongqu.commons.interceptor;

import com.zhumuchang.dongqu.commons.annotation.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author sx
 * @Description token拦截器
 * @Date 2022/3/12 19:17
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * token密钥
     */
    private String tokenSecret;

    private TokenUser tokenUser;

    public JwtInterceptor(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        String token = request.getHeader("token");
//        String userId = request.getHeader("userId");
        String requestMethod = request.getMethod();

        //如果不是直接映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            log.info("未映射到方法 - 放行请求：{}， 地址：{}", requestMethod, requestURL);
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //获取方法上面是否有不校验token注解
        PassToken passToken = method.getAnnotation(PassToken.class);
        if (null != passToken) {
            //有不校验token注解直接放行
            return true;
        }
        if (/*StringUtils.isEmpty(userId) || */StringUtils.isEmpty(token)) {
//            log.info("用户ID或Token为空 - 请求方法：{}， 地址：{}， userId：{}， token：{}", method, requestURL, userId, token);
            log.info("用户ID或Token为空 - 请求方法：{}， 地址：{}， token：{}", method, requestURL, token);
            return false;
        }
        // 验证 token
        tokenUser = JwtUtil.checkSign(token, null, tokenSecret);
        if (null == tokenUser) {
            log.info("TOKEN 验证失败 - 请求方法：{}， 地址：{}", method, requestURL);
            return false;
        }
        //把tokenUser放到请求中，在controller上可以拿到
        request.setAttribute("tokenUser", tokenUser);
        return true;
    }
}
