package com.zhumuchang.dongqu.mapper.shop;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.shop.SesameClerk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 店员 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Repository
@Mapper
public interface SesameClerkMapper extends BaseMapper<SesameClerk> {

    /**
     * 根据用户ID和店铺ID获取关联数量（用于判断该用户是否是店铺的店员）
     *
     * @param userId 用户ID
     * @param shopId 店铺ID
     * @return 数量
     */
    Integer checkShopExistenceClerk(@Param("userId") String userId, @Param("shopId") Integer shopId);
}
