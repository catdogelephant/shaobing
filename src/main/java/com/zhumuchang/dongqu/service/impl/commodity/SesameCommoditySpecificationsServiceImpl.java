package com.zhumuchang.dongqu.service.impl.commodity;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommoditySpecifications;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommoditySpecificationsDto;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommoditySpecificationsService;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommoditySpecificationsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品规格 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
@Slf4j
@Service
public class SesameCommoditySpecificationsServiceImpl extends ServiceImpl<SesameCommoditySpecificationsMapper, SesameCommoditySpecifications> implements SesameCommoditySpecificationsService {

    @Autowired
    private SesameCommoditySpecificationsMapper sesameCommoditySpecificationsMapper;

    /**
     * 商品
     */
    @Autowired
    private SesameCommodityService sesameCommodityService;

    /**
     * 设置商品规格
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     */
    @Override
    public void addCommoditySpecifications(TokenUser tokenUser, ReqAddCommoditySpecificationsDto param) {
        CommodityDto commodityDto = this.checkAddCommoditySpecificationsParam(tokenUser, param);
        SesameCommoditySpecifications specifications = this.createBean(param.getCommodityId(), param.getSpecificationsName(),
                commodityDto.getCommodityName(), param.getPrice(), commodityDto.getPrice(), param.getPriceFloat(),
                param.getStock(), param.getThumbnail(), param.getLimitBuy(), tokenUser.getUserId(), tokenUser.getUserName());
        boolean save = this.save(specifications);
        if (!save) {
            log.info("设置商品规格 - 保存失败 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            throw new BusinessException(BusinessEnum.FAIL);
        }
    }

    /**
     * 创建商品规格对象
     *
     * @param commodityId        商品主键ID
     * @param specificationsName 规格名称
     * @param commodityName      商品名称
     * @param price              商品规格价格
     * @param commodityPrice     商品主价格
     * @param priceFloat         价格浮动
     * @param stock              库存
     * @param thumbnail          规格缩略图
     * @param limitBuy           商品限购数量（为空或者等于0时代表不限制购买数量）
     * @param userId             创建人ID
     * @param userName           创建人姓名
     * @return 规格对象
     */
    private SesameCommoditySpecifications createBean(Integer commodityId, String specificationsName, String commodityName,
                                                     BigDecimal price, BigDecimal commodityPrice, Integer priceFloat, Integer stock,
                                                     String thumbnail, Integer limitBuy, String userId, String userName) {
        SesameCommoditySpecifications specifications = new SesameCommoditySpecifications();
        specifications.setOpenId(IdUtil.simpleUUID());
        specifications.setSesameCommodityId(commodityId);
        specifications.setName(specificationsName);
        specifications.setCommodityName(commodityName);
        specifications.setPrice(price);
        specifications.setCommodityPrice(commodityPrice);
        specifications.setPriceFloat(priceFloat);
        specifications.setStock(stock);
        specifications.setThumbnail(thumbnail);
        specifications.setLimitBuy(limitBuy);
        specifications.setDelFlag(1);
        specifications.setCreatedId(userId);
        specifications.setCreatedName(userName);
        specifications.setCreatedTime(LocalDateTime.now());
        specifications.setUpdatedId(userId);
        specifications.setUpdatedName(userName);
        return specifications;
    }

    private CommodityDto checkAddCommoditySpecificationsParam(TokenUser tokenUser, ReqAddCommoditySpecificationsDto param) {
        if (null == tokenUser || null == param) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        String commodityOpenId = param.getCommodityOpenId();
        if (StringUtils.isBlank(commodityOpenId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品ID不能为空");
        }
        //获取商品信息
        CommodityDto dtoByOpenId = sesameCommodityService.getDtoByOpenId(commodityOpenId);
        if (null == dtoByOpenId) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "商品信息不存在");
        }
        param.setCommodityId(dtoByOpenId.getId());
        //判断当前操作人能否操作该商品数据
        Integer count = sesameCommodityService.checkClerkAllowCommodityById(tokenUser.getUserId(), dtoByOpenId.getId());
        if ((null == count || count == 0) ? Boolean.TRUE : Boolean.FALSE) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        if (StringUtils.isBlank(param.getSpecificationsName())) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品规格名称不能为空");
        }
        if (param.getSpecificationsName().length() > 20) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品规格名称最多20字");
        }
        if (null == param.getStock()) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "库存不能为空");
        }
        if (9999 < param.getStock()) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "库存最多9999个");
        }
        if (StringUtils.isBlank(param.getThumbnail())) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品规格缩略图不能为空");
        }
        //校验商品规格是否重复
        List<String> commoditySpecificationsNameList = sesameCommoditySpecificationsMapper.getSpecificationsNameListByCommodityId(dtoByOpenId.getId());
        if (null != commoditySpecificationsNameList && !commoditySpecificationsNameList.isEmpty()) {
            if (commoditySpecificationsNameList.size() > 19) {
                throw new BusinessException(BusinessEnum.DATA_BUSINESS_ERROR.getCode(), "商品规格最多20个，目前已有" + commoditySpecificationsNameList.size() + "个，不可继续添加");
            }
            if (commoditySpecificationsNameList.contains(param.getSpecificationsName())) {
                throw new BusinessException(BusinessEnum.DATA_BUSINESS_ERROR.getCode(), "该商品规格名称已存在");
            }
        }
        BigDecimal commodityPrice = dtoByOpenId.getPrice();
        //设置规格价格
        param.setPrice(commodityPrice);
        //当规格浮动价格为负数的时候，跟商品主价格计算看是否小于0
        if (null != param.getPriceFloat()) {
            if (param.getPriceFloat().compareTo(0) < 0) {
                BigDecimal commodityFloatPrice = commodityPrice.add(new BigDecimal(String.valueOf(param.getPriceFloat())));
                if (commodityFloatPrice.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BusinessException(BusinessEnum.PARAM_ERROR.getCode(), "商品规格浮动价格设置太低，不应小于商品主价格的相反数，商品主价格为" + commodityPrice);
                }
            }
            //更新规格价格
            param.setPrice(param.getPrice().add(new BigDecimal(String.valueOf(param.getPriceFloat()))));
        }
        return dtoByOpenId;
    }
}
