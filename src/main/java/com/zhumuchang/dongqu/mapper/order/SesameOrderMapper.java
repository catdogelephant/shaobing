package com.zhumuchang.dongqu.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-08-19
 */
@Repository
@Mapper
public interface SesameOrderMapper extends BaseMapper<SesameOrder> {

    /**
     * 保存并返回主键id
     *
     * @param sesameOrder 订单信息
     * @return 主键id
     */
    Integer saveRetureId(@Param("sesameOrder") SesameOrder sesameOrder);
}
