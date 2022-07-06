package com.zhumuchang.dongqu.api.service.local;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.local.LocalSystemUser;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-04
 */
public interface LocalSystemUserService extends IService<LocalSystemUser> {

    void tran();
}
