package com.zhumuchang.dongqu.service.impl.commodity;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommodityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-07
 */
@Service
public class SesameCommodityServiceImpl extends ServiceImpl<SesameCommodityMapper, SesameCommodity> implements SesameCommodityService {

    @Autowired
    private SesameCommodityMapper sesameCommodityMapper;

    /**
     * 根据商品对外ID获取商品详情
     *
     * @param openId 商品对外ID
     * @return 商品详情
     */
    @Override
    public RespCommodityDetailDto commodityDetail(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        RespCommodityDetailDto resp = sesameCommodityMapper.commodityDetail(openId);
        if (null == resp) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND);
        }
        return resp;
    }
}
