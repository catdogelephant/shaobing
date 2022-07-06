package com.zhumuchang.dongqu.service.impl.test;

/**
 * @Author sx
 * @Description 枚举
 * @Date 2022/6/6 16:49
 */
public enum TestEnum {

    /**
     * 飞书
     */
    FEISHU("feishu"),

    /**
     * 钉钉
     */
    DINGTALK("dingtalk"),

    /**
     * 厦门创投oa
     */
    OA("oa");

    private final String oauthType;

    public String getOauthType() {
        return oauthType;
    }

    TestEnum(String oauthType) {
        this.oauthType = oauthType;
    }
}
