package com.zhumuchang.dongqu.commons.handle.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.api.properties.EncryptProperties;
import com.zhumuchang.dongqu.commons.annotation.Encrypt;
import com.zhumuchang.dongqu.commons.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @Author sx
 * @Description 加密返回值
 * @Date 2022/8/12 17:49
 */
@Slf4j
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<ResultDto> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断方法上是否有 @Encrypt 注解修饰
        boolean flag = returnType.hasMethodAnnotation(Encrypt.class);
        return flag;
    }

    @Override
    public ResultDto beforeBodyWrite(ResultDto body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //获取自定义加解密key的byte数组
        byte[] keyBytes = encryptProperties.getKey().getBytes();
        String key = encryptProperties.getKey();
        try {
            //当返回值的msg不为空时，加密msg
            if (StringUtils.isNotBlank(body.getMsg())) {
                body.setMsg(AESUtil.aesEncrypt(body.getMsg(), key));
            }
            //当返回值的data不为空时，加密data
            if (Objects.nonNull(body.getData())) {
                body.setData(AESUtil.aesEncrypt(objectMapper.writeValueAsString(body.getData()), key));
            }
            //状态码不进行加密
        } catch (Exception e) {
            log.error("加密返回值 - 加密异常", e);
            return ResultDto.fail();
        }
        return body;
    }
}
