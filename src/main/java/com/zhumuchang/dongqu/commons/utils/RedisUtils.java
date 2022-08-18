package com.zhumuchang.dongqu.commons.utils;

/**
 * @Author sx
 * @Description redis工具类
 * @Date 2022/8/17 19:42
 */
public class RedisUtils {

    /**
     * 幂等key前缀
     */
    public static final String SECRET_KEY = "SECRET:KEY";

    /**
     * 商品访问UV统计key
     */
    public static final String COMMODITY_VISIT_UV_KEY = "COMMODITY_VISIT_UV";

    public static String getKey(String key, String... joinKey) {
        StringBuilder stringBuilder = new StringBuilder(key);
        for (String join : joinKey) {
            stringBuilder.append(":").append(join);
        }
        return stringBuilder.toString();
    }
}
