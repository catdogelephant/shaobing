package com.zhumuchang.dongqu.config.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import com.zhumuchang.dongqu.config.utils.JacksonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    public static TokenUser checkSign(String token,String userId,String tokenSecret) {

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
                || ! jsonObject.get("userId").toString().equals(userId)){
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
