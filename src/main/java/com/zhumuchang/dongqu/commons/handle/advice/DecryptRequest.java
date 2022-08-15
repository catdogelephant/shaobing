package com.zhumuchang.dongqu.commons.handle.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumuchang.dongqu.api.properties.EncryptProperties;
import com.zhumuchang.dongqu.commons.annotation.Decrypt;
import com.zhumuchang.dongqu.commons.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @Author sx
 * @Description 解密请求参数
 * @Date 2022/8/12 18:15
 */
@Slf4j
@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断方法上面是否有解密注解
        boolean hasMethod = methodParameter.hasMethodAnnotation(Decrypt.class);
        //判断参数上是否有解密注解
        boolean hasParameter = methodParameter.hasParameterAnnotation(Decrypt.class);
        return hasMethod || hasParameter;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] body = new byte[inputMessage.getBody().available()];
        inputMessage.getBody().read(body);
        String bodyStr = new String(body, StandardCharsets.UTF_8);
        try {
            String decryptStr = AESUtil.aesDecrypt(bodyStr, encryptProperties.getKey());
            byte[] decrypt = decryptStr.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(decrypt);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() throws IOException {
                    return bais;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            log.error("解密请求参数 - 解密异常", e);
            return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
        }
    }
}
