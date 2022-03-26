package com.zhumuchang.dongqu.service.impl.user;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.user.SystemUser;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import com.zhumuchang.dongqu.api.service.SystemUserService;
import com.zhumuchang.dongqu.config.interceptor.JwtUtil;
import com.zhumuchang.dongqu.config.utils.PwUtils;
import com.zhumuchang.dongqu.mapper.user.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

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
        SystemUser systemUser = systemUserMapper.login(param);
        LoginTokenDto resp = new LoginTokenDto();
        if (null == systemUser) {
            log.info("登录 - 用户不存在 - param={}", JSONObject.toJSON(param));
            resp.setRespMsg("该用户不存在");
            return resp;
        }
        //校验密码
        boolean flag = PwUtils.checkPw(param.getPassword(), systemUser.getPassword(), systemUser.getId().toString());
        if (!flag) {
            log.info("登录 - 密码错误 - param={}", JSONObject.toJSON(param));
            resp.setRespMsg("密码错误");
            return resp;
        }
        //生成token
        JSONObject subject = new JSONObject();
        subject.put("userId", systemUser.getId());
        subject.put("userName", systemUser.getName());
        String token = JwtUtil.getToken(subject, tokenTimeOut);
        resp.setToken(token);
        //放入缓存
        redisTemplate.opsForValue().set("token", token, tokenTimeOut, TimeUnit.SECONDS);
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
        SystemUser systemUser = new SystemUser()
                .setOpenId(IdUtil.simpleUUID())
                .setName(param.getUserName())
                .setPhone(param.getPhone())
                .setAccount(param.getPhone())
                .setCreatedTime(LocalDateTime.now());
        Integer id = systemUserMapper.insertSystemUser(systemUser);
        //获取密码
        String password = PwUtils.processPw("123456", id.toString());
        SystemUser updateUser = new SystemUser()
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
