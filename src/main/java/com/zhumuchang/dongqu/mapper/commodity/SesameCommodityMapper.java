package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.AppCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.AppSpecificationsListDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Repository
@Mapper
public interface SesameCommodityMapper extends BaseMapper<SesameCommodity> {

    /**
     * 根据商品对外ID获取商品详情
     *
     * @param openId 商品对外ID
     * @return 商品详情
     */
    RespCommodityDetailDto commodityDetail(String openId);

    /**
     * 根据店铺主键ID获取该店铺下未删除的商品数量
     *
     * @param shopId 店铺主键ID
     * @return 商品数量
     */
    Integer countByShopId(Integer shopId);

    /**
     * 根据商品对外ID获取商品信息
     *
     * @param commodityOpenId 商品对外ID
     * @return 商品信息
     */
    CommodityDto getDtoByOpenId(String commodityOpenId);

    /**
     * 更新商品停启用状态
     *
     * @param id       商品主键ID
     * @param enable   停启用状态 0.停用 1.启用
     * @param userId   操作人ID
     * @param userName 操作人姓名
     * @return 更新条数
     */
    Integer updateEnableById(@Param("id") Integer id, @Param("enable") Integer enable, @Param("userId") String userId, @Param("userName") String userName);

    /**
     * 根据商品对外ID获取店铺主键ID
     *
     * @param commodityOpenId 商品对外ID
     * @return 店铺主键ID
     */
    Integer getShopIdByOpenId(String commodityOpenId);

    /**
     * 根据商品对外ID删除商品
     *
     * @param commodityOpenId 商品对外ID
     * @param userId          操作人ID
     * @param userName        操作人姓名
     * @return 操作结果
     */
    Integer delCommodityByOpenId(@Param("commodityOpenId") String commodityOpenId, @Param("userId") String userId, @Param("userName") String userName);

    /**
     * 商品分页列表
     *
     * @param page       分页参数
     * @param shopId     店铺ID
     * @param categoryId 品类ID
     * @return 分页列表
     */
    Page<RespCommodityPageDto> commodityPageByShopId(@Param("page") Page<RespCommodityPageDto> page, @Param("shopId") Integer shopId,
                                                     @Param("categoryId") Integer categoryId);

    /**
     * 判断当前用户是否是该店铺的店员
     *
     * @param userId      店员userId
     * @param commodityId 商品ID
     * @return 数量
     */
    Integer checkClerkAllowCommodityById(@Param("userId") String userId, @Param("commodityId") Integer commodityId);

    /**
     * 获取商品分页列表
     *
     * @param page       分页参数
     * @param categoryId 品类主键ID
     * @return 分页列表
     */
    Page<RespCommodityPageDto> appCommodityPage(@Param("page") Page<RespCommodityPageDto> page, @Param("categoryId") Integer categoryId,
                                                @Param("shopId") Integer shopId);

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
     * @param commodityId 商品主键ID
     * @return 商品规格集合
     */
    List<AppSpecificationsListDto> appCommoditySpecificationsList(Integer commodityId);

    /**
     * 根据商品主键ID获取商品名称
     *
     * @param sesameCommodityId 商品主键ID
     * @return 商品名称
     */
    String getNameById(Integer sesameCommodityId);
}
