package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAppCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqRelCommodityToCategoryDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.AppCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.AppSpecificationsListDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.page.StringPageDto;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

import java.util.List;

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

    /**
     * 设置商品所属的品类
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     */
    void relCommodityToCategory(TokenUser tokenUser, ReqRelCommodityToCategoryDto param);

    /**
     * 根据商品对外ID获取商品信息
     *
     * @param openId 商品对外ID
     * @return 商品信息
     */
    CommodityDto getDtoByOpenId(String openId);

    /**
     * 判断当前用户是否是该店铺的店员
     *
     * @param userId      店员userId
     * @param commodityId 商品ID
     * @return 数量
     */
    Integer checkClerkAllowCommodityById(String userId, Integer commodityId);

    /**
     * 获取商品分页列表
     *
     * @param param 品类对外ID
     * @return 商品分页列表
     */
    Page<RespCommodityPageDto> appCommodityPage(StringPageDto param);

    /**
     * 获取店铺里对应品类下的商品分页列表
     *
     * @param param 请求参数
     * @return 商品分页列表
     */
    Page<RespCommodityPageDto> appShopCommodityPage(ReqAppCommodityPageDto param);

    /**
     * 获取商品详情
     *
     * @param commodityOpenId 商品对外ID
     * @return 商品详情
     */
    AppCommodityDetailDto appCommodityDetail(String commodityOpenId);

    /**
     * 获取商品规格列表
     *
     * @param commodityOpenId 商品对外ID
     * @return 商品规格列表
     */
    List<AppSpecificationsListDto> appCommoditySpecificationsList(String commodityOpenId);
}
