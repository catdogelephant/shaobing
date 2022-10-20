package com.zhumuchang.dongqu.service.impl.shop;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.shop.SesameShop;
import com.zhumuchang.dongqu.api.service.shop.SesameShopService;
import com.zhumuchang.dongqu.mapper.shop.SesameShopMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Slf4j
@Service
public class SesameShopServiceImpl extends ServiceImpl<SesameShopMapper, SesameShop> implements SesameShopService {

    @Autowired
    private SesameShopMapper sesameShopMapper;

    /**
     * 根据店铺ID获取店铺停启用状态
     *
     * @param shopId 店铺ID
     * @return 停启用 0.停用 1.启用（默认停用）
     */
    @Override
    public Integer getEnableById(Integer shopId) {
        if (null == shopId) {
            log.info("根据商店ID判断店铺是否启用 - 店铺ID为空");
            return null;
        }
        Integer enable = sesameShopMapper.getEnableById(shopId);
        return enable;
    }

    /**
     * 根据ID获取店铺名称
     *
     * @param sesameShopId 店铺主键ID
     * @return 店铺名称
     */
    @Override
    public String getNameById(Integer sesameShopId) {
        String name = sesameShopMapper.getNameById(sesameShopId);
        return name;
    }
}
