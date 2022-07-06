package com.zhumuchang.dongqu.commons.utils;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 密码处理
 * MD5加密原始明文密码 -> 将结果再次MD5，-> 结果 append userId 后再次MD5 <br/>
 * MD5(MD5(MD5("password")) + "userId")
 *
 * @author sx
 * @version 1.0.0
 * @ClassName PwUtils
 * @data 2020/11/26 5:05 下午
 */
@Slf4j
public class PwUtils {

    /**
     * @param password 明文密码
     * @param salt     盐🧂
     * @Return: java.lang.String
     * @Author: sx
     * @Date: 2020/11/26
     **/
    public static String processPw(String password, String salt) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(salt)) {
            log.info("密码工具类 - 获取密码 - 参数为空 - password={}, salt={}", password, salt);
            return null;
        }
        return SecureUtil.md5(SecureUtil.md5(SecureUtil.md5(password)) + salt);
    }

    /**
     * @param password 明文密码
     * @param secret   密文密码
     * @param salt     盐🧂
     * @Return: boolean
     * @Author: sx
     * @Date: 2020/11/26
     **/
    public static boolean checkPw(String password, String secret, String salt) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(salt)) {
            log.info("密码工具类 - 校验密码 - 参数为空 - password={}, salt={}", password, salt);
            return false;
        }
        return secret.equals(processPw(password, salt));
    }

}
