package com.zhumuchang.dongqu.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommoditySpecifications;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}