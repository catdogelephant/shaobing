package com.zhumuchang.dongqu.service.impl.order;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.order.SesameAddress;
import com.zhumuchang.dongqu.api.dto.order.req.ReqAddAddressDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespAddressDetailDto;
import com.zhumuchang.dongqu.api.service.order.SesameAddressService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.mapper.order.SesameAddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-08-23
 */
@Slf4j
@Service
public class SesameAddressServiceImpl extends ServiceImpl<SesameAddressMapper, SesameAddress> implements SesameAddressService {

    @Autowired
    private SesameAddressMapper sesameAddressMapper;

    /**
     * 添加收货地址
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAddress(TokenUser tokenUser, ReqAddAddressDto param) {
        //判断当前收货地址是否为默认地址，如果是的话，则更新数据库中的默认地址为非默认地址
        if (ConstantsUtils.CODE_1.equals(param.getIsDefault())) {
            Boolean update = sesameAddressMapper.updateToNotDefaultAddrByUserId(tokenUser.getUserId());
            if (!Boolean.TRUE.equals(update)) {
                log.info("添加收货地址 - 更新默认收货地址失败");
            }
        }
        //新增地址
        SesameAddress sesameAddress = new SesameAddress();
        sesameAddress.setOpenId(IdUtil.simpleUUID());
        sesameAddress.setUserId(Integer.valueOf(tokenUser.getUserId()));
        sesameAddress.setConsigneeName(param.getConsigneeName());
        sesameAddress.setConsigneePhone(param.getConsigneePhone());
        sesameAddress.setProvince(param.getProvince());
        sesameAddress.setCity(param.getCity());
        sesameAddress.setArea(param.getArea());
        sesameAddress.setStreet(param.getStreet());
        sesameAddress.setDetailedAddress(param.getDetailedAddress());
        sesameAddress.setIsDefault(null == param.getIsDefault() ? 0 : param.getIsDefault());
        sesameAddress.setDelFlag(1);
        sesameAddress.setCreatedId(tokenUser.getUserId());
        sesameAddress.setCreatedName(tokenUser.getUserName());
        sesameAddress.setCreatedTime(LocalDateTime.now());
        sesameAddress.setUpdatedId(tokenUser.getUserId());
        sesameAddress.setUpdatedName(tokenUser.getUserName());
        boolean save = this.save(sesameAddress);
        if (!save) {
            log.info("添加收货地址 - 新增失败 - sesameAddress={}", JSONObject.toJSONString(sesameAddress));
        }
    }

    /**
     * 获取收货地址详情
     *
     * @param tokenUser     用户信息
     * @param addressOpenId 收货地址对外ID
     * @return 收货地址详情
     */
    @Override
    public RespAddressDetailDto getAddressDetail(TokenUser tokenUser, String addressOpenId) {
        RespAddressDetailDto resp = sesameAddressMapper.getAddressDetail(tokenUser.getUserId(), addressOpenId);
        if (null == resp) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "未查询到数据，请稍后重试");
        }
        return resp;
    }

    /**
     * 查询收货地址列表
     *
     * @param tokenUser 用户信息
     * @return 收货地址列表
     */
    @Override
    public Page<RespAddressDetailDto> pageAddress(TokenUser tokenUser) {
        Page<RespAddressDetailDto> page = new Page<>(1, 10);
        page = sesameAddressMapper.pageAddress(tokenUser.getUserId(), page);
        return page;
    }
}
