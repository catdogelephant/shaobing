package com.zhumuchang.dongqu.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.order.SesameAddress;
import com.zhumuchang.dongqu.api.dto.order.resp.RespAddressDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 获取收货地址详情
     *
     * @param userId        会员ID
     * @param addressOpenId 收货地址对外ID
     * @return 收货地址详情
     */
    RespAddressDetailDto getAddressDetail(@Param("userId") String userId, @Param("addressOpenId") String addressOpenId);
}
