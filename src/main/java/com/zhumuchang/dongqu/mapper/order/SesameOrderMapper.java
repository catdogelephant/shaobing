package com.zhumuchang.dongqu.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderDetailDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespOrderPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 获取订单分页列表
     *
     * @param page   分页
     * @param status 订单状态 0.待支付 1.待发货 2.待收货 3.待评价 4.已完成 5.已取消 6.已退款 7.退换中 8.申诉中
     * @return 订单分页列表
     */
    Page<RespOrderPageDto> getOredrPage(@Param("page") Page<RespOrderPageDto> page, @Param("status") Integer status, @Param("userId") String userId);

    /**
     * 获取订单详情
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return 订单详情
     */
    RespOrderDetailDto getOrderDetail(@Param("userId") String userId, @Param("orderId") Integer orderId);

    /**
     * 根据用户id和订单id获取订单id，用来校验该订单是否是该用户的
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return 订单id
     */
    Integer checkOrderIdToUser(@Param("userId") String userId, @Param("orderId") Integer orderId);

    /**
     * 删除订单
     *
     * @param orderId 订单id
     * @return 操作数
     */
    Integer delOrderById(Integer orderId);

    /**
     * 消息队列取消订单
     *
     * @param list 订单id集合
     */
    void queueCancelOrderByList(@Param("list") List<Integer> list);
}
