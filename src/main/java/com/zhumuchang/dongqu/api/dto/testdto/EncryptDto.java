package com.zhumuchang.dongqu.api.dto.testdto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description mybatis测试加解密类
 * @Date 2022/8/8 10:25
 */
@Data
public class EncryptDto implements Serializable {
    private static final long serialVersionUID = 6934028735297921867L;

    private String value;

    public EncryptDto(String value) {
        this.value = value;
    }

    public EncryptDto() {}
}
