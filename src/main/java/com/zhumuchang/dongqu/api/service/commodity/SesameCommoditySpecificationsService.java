package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommoditySpecifications;
import com.zhumuchang.dongqu.api.dto.commodity.SpecificationsDto;
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

    /**
     * 根据规格对外ID获取规格信息
     *
     * @param specificationsOpenId 商品规格对外ID
     * @return 规格信息
     */
    SpecificationsDto getByOpenId(String specificationsOpenId);
}
