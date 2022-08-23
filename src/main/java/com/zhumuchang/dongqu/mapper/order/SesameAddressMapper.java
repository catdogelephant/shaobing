package com.zhumuchang.dongqu.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.order.SesameAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 收货地址 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-08-23
 */
@Repository
@Mapper
public interface SesameAddressMapper extends BaseMapper<SesameAddress> {

    /**
     * 将指定的默认地址改为非默认地址
     *
     * @param userId 会员ID
     * @return 成功失败
     */
    Boolean updateToNotDefaultAddrByUserId(String userId);
}
