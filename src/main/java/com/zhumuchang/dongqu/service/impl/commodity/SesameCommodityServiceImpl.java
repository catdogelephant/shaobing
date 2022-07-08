package com.zhumuchang.dongqu.service.impl.commodity;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.api.service.shop.SesameClerkService;
import com.zhumuchang.dongqu.api.service.shop.SesameShopService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.constants.TableConstants;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.mapper.SesameMapper;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommodityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 店铺
     */
    @Autowired
    private SesameShopService sesameShopService;

    /**
     * 基Mapper
     */
    @Autowired
    private SesameMapper sesameMapper;

    /**
     * 店员
     */
    @Autowired
    private SesameClerkService sesameClerkService;

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

    /**
     * 新增商品
     *
     * @param param 请求参数
     */
    @Override
    public void addCommodity(ReqAddCommodityDto param, TokenUser tokenUser) {
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.NO_TOKEN);
        }
        Pattern compile = Pattern.compile(ConstantsUtils.CHECK_WORK_MILEAGE_STR);
        Matcher matcher = compile.matcher(param.getCommodityPrice().toString());
        boolean matches = matcher.matches();
        if (!matches) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR.getCode(), "商品价格最多两位小数");
        }
        //获取店铺id
        String shopId = sesameMapper.getNotDelIdByOpenId(param.getShopId(), TableConstants.SESAME_SHOP_TABLE_NAME);
        if (StringUtils.isBlank(shopId)) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "店铺信息错误");
        }
        //判断店铺是否正常
        Integer shopEnable = sesameShopService.getEnableById(shopId);
        if (null == shopEnable || shopEnable == 0) {
            throw new BusinessException(BusinessEnum.DATA_BUSINESS_ERROR.getCode(), "店铺已停用，不可进行操作");
        }
        //判断当前用户是否是店铺的店员
        Boolean shopClerkFlag = sesameClerkService.checkShopExistenceClerk(tokenUser.getUserId(), shopId);
        if (!Boolean.TRUE.equals(shopClerkFlag)) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        //设置商品图片集合
        String pictureListJson = JSONObject.toJSONString(param.getPictureList());
        //获取店铺下的商品数量
        Integer count = sesameCommodityMapper.countByShopId(shopId);
        //创建商品
        SesameCommodity commodity = this.createCommodity(shopId, param.getCommodityName(), param.getCommodityPrice(), param.getIntroduce(), param.getThumbnail(),
                pictureListJson, param.getLimitBuy(), count + 1, 1, tokenUser.getUserId(), tokenUser.getUserName());
        boolean save = this.save(commodity);
        if (!save) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "保存商品失败");
        }
    }

    /**
     * 创建商品对象
     *
     * @param sesameShopId    店铺主键ID
     * @param commodityName   商品名称
     * @param commodityPrice  商品价格
     * @param introduce       商品简介
     * @param thumbnail       商品缩略图
     * @param pictureListJson 商品图片集合JSON
     * @param limitBuy        限购数量（为空或者等于0时代表不限制购买数量）
     * @param sort            排序
     * @param enable          停启用 0.停用 1.启用
     * @param createdId       创建人ID
     * @param createdName     创建人姓名
     * @return 商品对象
     */
    public SesameCommodity createCommodity(String sesameShopId, String commodityName, BigDecimal commodityPrice, String introduce, String thumbnail, String pictureListJson,
                                           Integer limitBuy, int sort, int enable, String createdId, String createdName) {
        SesameCommodity commodity = new SesameCommodity();
        commodity.setOpenId(IdUtil.simpleUUID());
        commodity.setSesameShopId(sesameShopId);
        commodity.setName(commodityName);
        commodity.setPrice(commodityPrice);
        commodity.setIntroduce(introduce);
        commodity.setThumbnail(thumbnail);
        commodity.setPictureJson(pictureListJson);
        commodity.setLimitBuy(limitBuy);
        commodity.setSort(sort);
        commodity.setEnable(enable);
        commodity.setDelFlag(1);
        commodity.setCreatedId(createdId);
        commodity.setCreatedName(createdName);
        commodity.setCreatedTime(LocalDateTime.now());
        commodity.setUpdateId(createdId);
        commodity.setUpdatedName(createdName);
        commodity.setUpdatedTime(LocalDateTime.now());
        return commodity;
    }
}
