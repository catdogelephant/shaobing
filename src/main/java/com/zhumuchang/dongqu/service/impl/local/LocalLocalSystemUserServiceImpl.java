package com.zhumuchang.dongqu.service.impl.local;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.local.LocalSystemUser;
import com.zhumuchang.dongqu.api.service.local.LocalSystemUserService;
import com.zhumuchang.dongqu.mapper.local.LocalSystemUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-04
 */
@Service
public class LocalLocalSystemUserServiceImpl extends ServiceImpl<LocalSystemUserMapper, LocalSystemUser> implements LocalSystemUserService {

    @Resource
    private LocalSystemUserMapper localSystemUserMapper;

    /**
     * 测试事务
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void tran() {
        LocalSystemUser localOne = new LocalSystemUser();
        localOne.setName("tran4");
        localSystemUserMapper.insert(localOne);
        LocalSystemUser localTwo = new LocalSystemUser();
        localTwo.setName("tran5");
        localSystemUserMapper.insert(localTwo);
        LocalSystemUser localThree = new LocalSystemUser();
        localThree.setName("tran6");
        localSystemUserMapper.insert(localThree);
        throw new NullPointerException("测试事务回滚");
    }
}
