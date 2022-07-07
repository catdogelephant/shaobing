package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import org.apache.ibatis.annotations.Mapper;
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
}
