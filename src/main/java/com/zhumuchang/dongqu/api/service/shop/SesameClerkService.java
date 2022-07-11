package com.zhumuchang.dongqu.api.service.shop;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.shop.SesameClerk;

/**
 * <p>
 * 店员 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
public interface SesameClerkService extends IService<SesameClerk> {

    /**
     * 判断当前用户是否是店铺的店员
     *
     * @param userId 用户ID
     * @param shopId 店铺ID
     * @return 是：true，否：false
     */
    Boolean checkShopExistenceClerk(String userId, Integer shopId);
}
