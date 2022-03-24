package com.zhumuchang.dongqu.api.bean.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 继承user
 * @Date 2022/3/19 10:51
 */
@Data
public class ExSystemUser implements Serializable {

    private String value;

    private String path;

}
