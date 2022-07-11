package com.zhumuchang.dongqu.api.service.shop;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.shop.SesameShop;

/**
 * <p>
 * 店铺 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
public interface SesameShopService extends IService<SesameShop> {

    /**
     * 根据店铺ID获取店铺停启用状态
     *
     * @param shopId 店铺ID
     * @return 停启用 0.停用 1.启用（默认停用）
     */
    Integer getEnableById(Integer shopId);
}
