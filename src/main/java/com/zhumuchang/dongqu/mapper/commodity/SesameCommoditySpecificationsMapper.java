package com.zhumuchang.dongqu.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommoditySpecifications;
import com.zhumuchang.dongqu.api.dto.commodity.SpecificationsDto;
import com.zhumuchang.dongqu.api.dto.order.other.OrderCommodityJsonDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqConfirmOrderDto;
import com.zhumuchang.dongqu.api.dto.order.resp.ConfirmOrderShopListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品规格 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
@Repository
@Mapper
public interface SesameCommoditySpecificationsMapper extends BaseMapper<SesameCommoditySpecifications> {

    /**
     * 根据商品ID获取规格名称集合
     *
     * @param commodityId 商品ID
     * @return 规格名称集合
     */
    List<String> getSpecificationsNameListByCommodityId(Integer commodityId);

    /**
     * 根据规格对外ID获取规格信息
     *
     * @param specificationsOpenId 商品规格对外ID
     * @return 规格信息
     */
    SpecificationsDto getByOpenId(String specificationsOpenId);

    /**
     * 根据规格对外ID获取规格集合
     *
     * @param param 请求参数
     * @return 规格集合
     */
    List<ConfirmOrderShopListDto> getDtoByOpenId(@Param("param") List<ReqConfirmOrderDto> param);

    /**
     * 获取订单的商品信息
     *
     * @param speOpenIds 规格对外IDs
     * @return 商品信息
     */
    List<OrderCommodityJsonDto> listOrderCommodity(String speOpenIds);
}
