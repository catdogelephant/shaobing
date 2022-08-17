package com.zhumuchang.dongqu.service.impl.user;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.user.SesameSystemUser;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.user.SystemUserService;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.JwtUtil;
import com.zhumuchang.dongqu.commons.utils.PwUtils;
import com.zhumuchang.dongqu.mapper.user.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-03-12
 */
@Slf4j
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SesameSystemUser> implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * redisTemplate
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Value("${jwt.token.timeout}")
    private long tokenTimeOut;

    @Override
    public LoginTokenDto login(LoginDto param) {
        SesameSystemUser sesameSystemUser = systemUserMapper.login(param);
        LoginTokenDto resp = new LoginTokenDto();
        if (null == sesameSystemUser) {
            log.info("登录 - 用户不存在 - param={}", JSONObject.toJSON(param));
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "用户不存在");
        }
        //校验密码
        boolean flag = PwUtils.checkPw(param.getPassword(), sesameSystemUser.getPassword(), sesameSystemUser.getId().toString());
        if (!flag) {
            log.info("登录 - 密码错误 - param={}", JSONObject.toJSON(param));
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "密码错误");
        }
        //设置redis的key
        String key = "TOKEN_" + sesameSystemUser.getId();
        //获取redis中的token
        String redisToken = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(redisToken)) {
            log.info("登录 - redis中token未过期");
            resp.setToken(redisToken);
            return resp;
        }
        //生成token
        JSONObject subject = new JSONObject();
        subject.put("userId", sesameSystemUser.getId());
        subject.put("userName", sesameSystemUser.getName());
        String token = JwtUtil.getToken(subject, tokenTimeOut);
        if (StringUtils.isEmpty(token)) {
            log.info("登录 - 获取token为空");
            resp.setRespMsg("登录失败");
            return resp;
        }
        resp.setToken(token);
        //放入缓存
        redisTemplate.opsForValue().set(key, token, tokenTimeOut, TimeUnit.SECONDS);
        return resp;
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

    @Override
    public String register(RegisterReq param) {
        SesameSystemUser sesameSystemUser = new SesameSystemUser()
                .setOpenId(IdUtil.simpleUUID())
                .setName(param.getUserName())
                .setPhone(param.getPhone())
                .setAccount(param.getPhone())
                .setCreatedTime(LocalDateTime.now());
        Integer id = systemUserMapper.insertSystemUser(sesameSystemUser);
        //获取密码
        String password = PwUtils.processPw("123456", id.toString());
        SesameSystemUser updateUser = new SesameSystemUser()
                .setId(id)
                .setPassword(password);
        boolean update = this.updateById(updateUser);
        if (!update) {
            log.info("注册 - 更新密码失败 - param={}", JSONObject.toJSON(param));
            return "注册失败";
        }
        return null;
    }
}
