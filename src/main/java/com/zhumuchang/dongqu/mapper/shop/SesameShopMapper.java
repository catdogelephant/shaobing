package com.zhumuchang.dongqu.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.shop.SesameShop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 店铺 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Repository
@Mapper
public interface SesameShopMapper extends BaseMapper<SesameShop> {

}
