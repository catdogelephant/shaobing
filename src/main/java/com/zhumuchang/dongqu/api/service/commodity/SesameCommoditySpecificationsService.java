package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommoditySpecifications;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommoditySpecificationsDto;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

/**
 * <p>
 * 商品规格 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
public interface SesameCommoditySpecificationsService extends IService<SesameCommoditySpecifications> {

    /**
     * 设置商品规格
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     */
    void addCommoditySpecifications(TokenUser tokenUser, ReqAddCommoditySpecificationsDto param);
}
