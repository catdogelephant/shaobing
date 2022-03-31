package com.zhumuchang.dongqu.api.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.user.SesameSystemUser;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import com.zhumuchang.dongqu.api.dto.user.req.RegisterReq;
import com.zhumuchang.dongqu.api.dto.user.resp.LoginTokenDto;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author sx
 * @since 2022-03-12
 */
public interface SystemUserService extends IService<SesameSystemUser> {

    /**
     * 登录
     *
     * @param param 请求参数
     * @return token信息
     */
    LoginTokenDto login(LoginDto param);

    /**
     * 注册
     *
     * @param param 请求参数
     * @return 异常信息
     */
    String register(RegisterReq param);
}
