package com.zhumuchang.dongqu.service.impl.commodity;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodity;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodityCategorySort;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCommodityPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqRelCommodityToCategoryDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityDetailDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCommodityPageDto;
import com.zhumuchang.dongqu.api.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityCategorySortService;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.api.service.shop.SesameClerkService;
import com.zhumuchang.dongqu.api.service.shop.SesameShopService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.constants.TableConstants;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.mapper.SesameMapper;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommodityMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
@Slf4j
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
     * 商品品类排序
     */
    @Autowired
    private SesameCommodityCategorySortService sesameCommodityCategorySortService;

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
        //图片集合JSON转List
        String pictureJson = resp.getPictureJson();
        if (StringUtils.isBlank(pictureJson)) {
            log.info("根据商品对外ID获取商品详情 - 图片集合JSON为空");
        } else {
            List pictureList = JSONObject.parseObject(pictureJson, List.class);
            resp.setPictureList(pictureList);
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
        Integer shopId = sesameMapper.getNotDelIdByOpenId(param.getShopId(), TableConstants.SESAME_SHOP_TABLE_NAME);
        if (null == shopId) {
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
        SesameCommodity commodity = this.createBean(shopId, param.getCommodityName(), param.getCommodityPrice(), param.getIntroduce(), param.getThumbnail(),
                pictureListJson, param.getLimitBuy(), count + 1, 1, tokenUser.getUserId(), tokenUser.getUserName());
        boolean save = this.save(commodity);
        if (!save) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "保存商品失败");
        }
    }

    /**
     * 停启用商品
     *
     * @param commodityOpenId 商品对外ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableCommodity(String commodityOpenId, TokenUser tokenUser) {
        if (StringUtils.isBlank(commodityOpenId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.NO_TOKEN);
        }
        //查询商品状态，当为null时说明商品不存在
        CommodityDto commodityDto = sesameCommodityMapper.getDtoByOpenId(commodityOpenId);
        if (null == commodityDto) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND);
        }
        //判断当前商品所在的店铺，是否包含当前用户店员信息
        Boolean shopClerkFlag = sesameClerkService.checkShopExistenceClerk(tokenUser.getUserId(), commodityDto.getSesameShopId());
        if (!Boolean.TRUE.equals(shopClerkFlag)) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        //更新商品状态
        int enable = 0;
        if (null == commodityDto.getEnable()) {
            log.info("停启用商品 - 实体数据异常 - commodityOpenId={}, commodityDto={}", commodityOpenId, JSONObject.toJSONString(commodityDto));
            throw new BusinessException(BusinessEnum.DATA_ERROR);
        }
        if (commodityDto.getEnable() == 0) {
            enable = 1;
        }
        Integer update = sesameCommodityMapper.updateEnableById(commodityDto.getId(), enable, tokenUser.getUserId(), tokenUser.getUserName());
        if (1 != update) {
            log.info("停启用商品 - 更新状态失败 - commodityOpenId={}, commodityDto={}, enable={}", commodityOpenId, JSONObject.toJSONString(commodityDto), enable);
            throw new BusinessException(BusinessEnum.FAIL);
        }
    }

    /**
     * 删除商品
     *
     * @param commodityOpenId 商品对外ID
     * @param tokenUser       tokenUser
     */
    @Override
    public void delCommodity(String commodityOpenId, TokenUser tokenUser) {
        if (StringUtils.isBlank(commodityOpenId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.NO_TOKEN);
        }
        //判断商品是否存在
        Integer shopId = sesameCommodityMapper.getShopIdByOpenId(commodityOpenId);
        if (null == shopId) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND);
        }
        //判断商品和操作人是否为同一店铺的
        Boolean shopClerkFlag = sesameClerkService.checkShopExistenceClerk(tokenUser.getUserId(), shopId);
        if (!Boolean.TRUE.equals(shopClerkFlag)) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        //删除商品
        Integer del = sesameCommodityMapper.delCommodityByOpenId(commodityOpenId, tokenUser.getUserId(), tokenUser.getUserName());
        if (1 != del) {
            log.info("删除商品 - 删除失败 - commodityOpenId={}, tokenUser={}", commodityOpenId, JSONObject.toJSONString(tokenUser));
            throw new BusinessException(BusinessEnum.FAIL);
        }
    }

    /**
     * 商品分页列表
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     * @return 商品分页列表
     */
    @Override
    public Page<RespCommodityPageDto> commodityPage(TokenUser tokenUser, ReqCommodityPageDto param) {
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.NO_TOKEN);
        }
        if (null == param || StringUtils.isBlank(param.getShopOpenId())) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "店铺ID为空");
        }
        //获取店铺ID
        Integer shopId = sesameMapper.getNotDelIdByOpenId(param.getShopOpenId(), TableConstants.SESAME_SHOP_TABLE_NAME);
        if (null == shopId) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "店铺信息错误");
        }
        //判断当前用户是否是店铺的店员
        Boolean shopClerkFlag = sesameClerkService.checkShopExistenceClerk(tokenUser.getUserId(), shopId);
        if (!Boolean.TRUE.equals(shopClerkFlag)) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        //获取品类ID
        Integer categoryId = sesameMapper.getNotDelIdByOpenId(param.getCategoryOpenId(), TableConstants.SESAME_CATEGORY_TABLE_NAME);
        Page<RespCommodityPageDto> page = new Page<>(param.getCurrent(), param.getSize());
        page = sesameCommodityMapper.commodityPageByShopId(page, shopId, categoryId);
        return page;
    }

    /**
     * 设置商品所属的品类
     *
     * @param tokenUser tokenUser
     * @param param     请求参数
     */
    @Override
    public void relCommodityToCategory(TokenUser tokenUser, ReqRelCommodityToCategoryDto param) {
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.NO_TOKEN);
        }
        //判断商品是否存在
        CommodityDto commodityDto = sesameCommodityMapper.getDtoByOpenId(param.getCommodityOpenId());
        if (null == commodityDto) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "商品不存在");
        }
        //判断品类是否存在
        Integer categoryId = sesameMapper.getNotDelIdByOpenId(param.getCategoryOpenId(), TableConstants.SESAME_CATEGORY_TABLE_NAME);
        if (null == categoryId) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "品类不存在");
        }
        //判断当前用户是否是该店铺的店员
        Integer count = sesameCommodityMapper.checkClerkAllowCommodityById(tokenUser.getUserId(), commodityDto.getId());
        if ((null == count || count == 0) ? Boolean.TRUE : Boolean.FALSE) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前用户不是该店铺的店员，不可进行操作");
        }
        //判断该商品是否已在当前品类下
        Integer relCount = sesameCommodityCategorySortService.countByCommodityIdAndCategoryId(commodityDto.getId(), categoryId);
        if ((null == relCount || relCount == 0) ? Boolean.FALSE : Boolean.TRUE) {
            throw new BusinessException(BusinessEnum.FAIL.getCode(), "当前商品已在当前品类中存在");
        }
        //获取该店铺在当前品类下已经有多少个商品
        Integer commodityCount = sesameCommodityCategorySortService.countByShopIdAndCategoryId(commodityDto.getSesameShopId(), categoryId);
        //把商品设置到当前品类下
        SesameCommodityCategorySort sesameCommodityCategorySort = sesameCommodityCategorySortService.createBean(commodityDto.getSesameShopId(), commodityDto.getId(),
                categoryId, commodityCount + 1, tokenUser.getUserId(), tokenUser.getUserName());
        boolean save = sesameCommodityCategorySortService.save(sesameCommodityCategorySort);
        if (!save) {
            log.info("设置商品所属的品类 - 保存失败 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSON(tokenUser));
            throw new BusinessException(BusinessEnum.FAIL);
        }
    }

    /**
     * 判断当前用户是否是该店铺的店员
     *
     * @param userId      店员userId
     * @param commodityId 商品ID
     * @return 数量
     */
    @Override
    public Integer checkClerkAllowCommodityById(String userId, Integer commodityId) {
        Integer count = sesameCommodityMapper.checkClerkAllowCommodityById(userId, commodityId);
        return count;
    }

    /**
     * 根据商品对外ID获取商品信息
     *
     * @param openId 商品对外ID
     * @return 商品信息
     */
    @Override
    public CommodityDto getDtoByOpenId(String openId) {
        CommodityDto commodityDto = sesameCommodityMapper.getDtoByOpenId(openId);
        return commodityDto;
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
    public SesameCommodity createBean(Integer sesameShopId, String commodityName, BigDecimal commodityPrice, String introduce, String thumbnail, String pictureListJson,
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
        commodity.setUpdatedId(createdId);
        commodity.setUpdatedName(createdName);
        commodity.setUpdatedTime(LocalDateTime.now());
        return commodity;
    }
}
