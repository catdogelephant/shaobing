package com.zhumuchang.dongqu.mapper.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.order.SesameOrderCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单商品表 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-08-26
 */
@Repository
@Mapper
public interface SesameOrderCommodityMapper extends BaseMapper<SesameOrderCommodity> {

}
