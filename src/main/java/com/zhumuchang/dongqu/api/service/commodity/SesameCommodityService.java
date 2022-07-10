package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
public interface SesameCommodityService extends IService<SesameCommodity> {

    /**
     * 根据商品对外ID获取商品详情
     *
     * @param openId 商品对外ID
     * @return 商品详情
     */
    RespCommodityDetailDto commodityDetail(String openId);

    /**
     * 新增商品
     *
     * @param param 请求参数
     */
    void addCommodity(ReqAddCommodityDto param, TokenUser tokenUser);

    /**
     * 停启用商品
     *
     * @param commodityOpenId 商品对外ID
     */
    void enableCommodity(String commodityOpenId, TokenUser tokenUser);

    /**
     * 删除商品
     *
     * @param commodityOpenId 商品对外ID
     * @param tokenUser       tokenUser
     */
    void delCommodity(String commodityOpenId, TokenUser tokenUser);

    /**
     * 商品分页列表
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     * @return 商品分页列表
     */
    Page<RespCommodityPageDto> commodityPage(TokenUser tokenUser, ReqCommodityPageDto param);
}
