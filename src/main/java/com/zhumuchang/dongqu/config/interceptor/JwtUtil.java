package com.zhumuchang.dongqu.config.interceptor;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.zhumuchang.dongqu.config.utils.JacksonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author sx
 * @Description jwt工具类
 * @Date 2022/3/12 19:29
 */
@Slf4j
public class JwtUtil {

    /**
     * token 加密串
     */
    private static final String DEFAULT_TOKEN_SECRET = "seeSSSEEE";

    /**
     * JWT 第二段信息密钥
     */
    private static final byte[] THE_SECOND_MESSAGE_KEY = {96, -76, 32, -69, 56, 81, -39, -44, 122, -53, -109, 61, -66, 112, 57, -101};

    /**
     * AES
     **/
    private static final cn.hutool.crypto.symmetric.AES AES = SecureUtil.aes(THE_SECOND_MESSAGE_KEY);

    /**
     * 获取token
     *
     * @param subject     加密数据（一般包括userId、userName、附加内容）
     * @param validPeriod 过期时间
     * @return token
     */
    public static String getToken(JSONObject subject, long validPeriod) {
        if (null == subject || null == subject.get("userId") || null == subject.get("userName")) {
            log.info("JwtUtil - 获取token - subject参数为空 - subject={}", subject);
            return null;
        }
        if (0 >= validPeriod) {
            log.info("JwtUtil - 获取token - 过期时间错误 - validPeriod={}", validPeriod);
            return null;
        }
        subject.put("rn", RandomUtil.randomString(8));
        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()//.setIssuer("goldnurse.com")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + validPeriod))
                .setSubject(Arrays.toString(AES.encrypt(JacksonUtil.toJson(subject))))
                .signWith(SignatureAlgorithm.HS256, DEFAULT_TOKEN_SECRET);
        String token = jwtBuilder.compact();
        return token;
    }

    public static TokenUser checkSign(String token, String userId, String tokenSecret) {

        JSONObject jsonObject;
        try {

            Claims body = Jwts.parser()
                    .setSigningKey(StringUtils.isEmpty(tokenSecret) ? DEFAULT_TOKEN_SECRET : tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            String subject = body.getSubject();
            subject = new String(AES.decrypt(strToByte(subject)));
            jsonObject = JacksonUtil.toEntity(subject, JSONObject.class);
        } catch (Exception e) {
            return null;
        }
        if (jsonObject == null
                || jsonObject.get("userId") == null
                || jsonObject.get("userName") == null
                || !jsonObject.get("userId").toString().equals(userId)) {
            log.info("TOKEN 信息无效");
            return null;
        }
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(userId);
        tokenUser.setUserName(jsonObject.get("userName").toString());
        tokenUser.setSubject(jsonObject);
        return tokenUser;
    }


    /**
     * 仅此可用<br/>
     * String str = "[-115, -109, -14]";<br/>
     * ⬇<br/>
     * byte[] bytes = {-115, -109, -14};
     **/
    private static byte[] strToByte(String str) {
        str = StrUtil.sub(str, 1, str.length() - 1).replace(" ", "");
        String[] split = str.split(",");
        byte[] bytes = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bytes[i] = Byte.parseByte(split[i]);
        }
        return bytes;
    }
}
