package com.zhumuchang.dongqu.config.interceptor;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenUser implements Serializable {

    private static final long serialVersionUID = -8509194935128439542L;
    String userId;
    String userName;
    JSONObject subject;
}
