package com.zhumuchang.dongqu.service.impl.user;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.user.SystemUser;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;
import com.zhumuchang.dongqu.api.service.SystemUserService;
import com.zhumuchang.dongqu.config.utils.PwUtils;
import com.zhumuchang.dongqu.mapper.user.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

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

    @DubboReference

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public LoginTokenDto login(LoginDto param) {
        String userId = systemUserMapper.login(param);
        if (StringUtils.isEmpty(userId)) {
            log.info("登录 - 用户不存在 - param={}", JSONObject.toJSON(param));
            return null;
        }

        return null;
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
