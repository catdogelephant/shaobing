package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
    Integer countByShopId(String shopId);

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
    Integer updateEnableById(@Param("id") String id, @Param("enable") Integer enable, @Param("userId") String userId, @Param("userName") String userName);
}
