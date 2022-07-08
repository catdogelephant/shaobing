package com.zhumuchang.dongqu.service.impl.shop;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.shop.SesameClerk;
import com.zhumuchang.dongqu.api.service.shop.SesameClerkService;
import com.zhumuchang.dongqu.mapper.shop.SesameClerkMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店员 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Slf4j
@Service
public class SesameClerkServiceImpl extends ServiceImpl<SesameClerkMapper, SesameClerk> implements SesameClerkService {

    @Autowired
    private SesameClerkMapper sesameClerkMapper;

    /**
     * 判断当前用户是否是店铺的店员
     *
     * @param userId 用户ID
     * @param shopId 店铺ID
     * @return 是：true，否：false
     */
    @Override
    public Boolean checkShopExistenceClerk(String userId, String shopId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(shopId)) {
            log.info("判断当前用户是否是店铺的店员 - 参数为空 - userId={}, shopId={}", userId, shopId);
            return Boolean.FALSE;
        }
        Integer count = sesameClerkMapper.checkShopExistenceClerk(userId, shopId);
        return (null == count || count == 0) ? Boolean.FALSE : Boolean.TRUE;
    }
}
