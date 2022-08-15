package com.zhumuchang.dongqu.api.dto.testdto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 加解密测试用户类
 * @Date 2022/8/12 18:25
 */
@Data
public class EncryptUser implements Serializable {
    private static final long serialVersionUID = -425647713284550266L;

    private String name;

    private String phone;
}
