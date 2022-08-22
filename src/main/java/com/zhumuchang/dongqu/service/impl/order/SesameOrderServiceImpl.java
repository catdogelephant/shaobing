package com.zhumuchang.dongqu.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import com.zhumuchang.dongqu.api.dto.commodity.SpecificationsDto;
import com.zhumuchang.dongqu.api.dto.order.req.ReqCartDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartCommodityListDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommoditySpecificationsService;
import com.zhumuchang.dongqu.api.service.order.SesameOrderService;
import com.zhumuchang.dongqu.api.service.shop.SesameShopService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.commons.utils.RedisUtils;
import com.zhumuchang.dongqu.mapper.SesameMapper;
import com.zhumuchang.dongqu.mapper.order.SesameOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-08-19
 */
@Slf4j
@Service
public class SesameOrderServiceImpl extends ServiceImpl<SesameOrderMapper, SesameOrder> implements SesameOrderService {

    @Autowired
    private SesameCommoditySpecificationsService sesameCommoditySpecificationsService;

    @Autowired
    private SesameMapper sesameMapper;

    /**
     * 店铺
     */
    @Autowired
    private SesameShopService sesameShopService;

    /**
     * 商品
     */
    @Autowired
    private SesameCommodityService sesameCommodityService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加购物车
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    @Override
    public void addCart(TokenUser tokenUser, ReqCartDto param) {
        if (null == tokenUser || null == param || StringUtils.isBlank(param.getSpecificationsOpenId()) || null == param.getNum()) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        if (param.getNum().compareTo(ConstantsUtils.CODE_0) < 0 || param.getNum().compareTo(ConstantsUtils.CODE_99) > 0) {
            throw new BusinessException(BusinessEnum.PARAM_ERROR.getCode(), "最多添加99个");
        }
        SpecificationsDto specificationsDto = sesameCommoditySpecificationsService.getByOpenId(param.getSpecificationsOpenId());
        if (null == specificationsDto) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND);
        }
        String shopName = sesameShopService.getNameById(specificationsDto.getSesameShopId());
        String commodityName = sesameCommodityService.getNameById(specificationsDto.getSesameCommodityId());
        //判断redis中是否有对应的商品
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        String cartKey = RedisUtils.getKey(RedisUtils.CART_KEY, tokenUser.getUserId());
        Object redisObj = hash.get(cartKey, specificationsDto.getSesameShopOpenId());
        RespCartDto respCartDto = null;
        //是否新建数据标识
        Boolean addRedisFlag = Boolean.TRUE;
        if (Objects.nonNull(redisObj)) {
            if (redisObj instanceof String) {
                String redisJson = (String) redisObj;
                respCartDto = JSONObject.parseObject(redisJson, RespCartDto.class);
                List<RespCartCommodityListDto> cartCommodityList = respCartDto.getCartCommodityList();
                for (RespCartCommodityListDto redisListDto : cartCommodityList) {
                    if (redisListDto.getCommodityOpenId().equals(specificationsDto.getSesameCommodityOpenId()) &&
                            redisListDto.getSpecificationsOpenId().equals(specificationsDto.getOpenId())) {
                        //redis中有完全一样的商品，则增加数量
                        redisListDto.setCommodityNum(redisListDto.getCommodityNum() + param.getNum());
                        redisListDto.setCommodityUpdateTime(LocalDateTime.now());
                        addRedisFlag = Boolean.FALSE;
                        break;
                    }
                }
            }
        }
        if (null == respCartDto) {
            respCartDto = new RespCartDto();
            respCartDto.setUserId(tokenUser.getUserId());
            respCartDto.setShopName(shopName);
            respCartDto.setShopOpenId(specificationsDto.getSesameShopOpenId());
            respCartDto.setUpdateTime(LocalDateTime.now());
            ArrayList<RespCartCommodityListDto> commodityList = Lists.newArrayList();
            respCartDto.setCartCommodityList(commodityList);
        }
        if (addRedisFlag) {
            RespCartCommodityListDto commodityListDto = new RespCartCommodityListDto();
            commodityListDto.setCommodityName(commodityName);
            commodityListDto.setCommodityNum(param.getNum());
            commodityListDto.setCommodityOpenId(specificationsDto.getSesameCommodityOpenId());
            commodityListDto.setCommodityPrice(specificationsDto.getPrice());
            commodityListDto.setSpecificationsName(specificationsDto.getName());
            commodityListDto.setSpecificationsOpenId(specificationsDto.getOpenId());
            commodityListDto.setCommodityUpdateTime(LocalDateTime.now());
            List<RespCartCommodityListDto> cartCommodityList = respCartDto.getCartCommodityList();
            cartCommodityList.add(commodityListDto);
        }
        String redisValue = JSONObject.toJSONString(respCartDto);
        hash.put(cartKey, specificationsDto.getSesameShopOpenId(), redisValue);
        Boolean expire = stringRedisTemplate.expire(cartKey, 3, TimeUnit.DAYS);
        if (!Boolean.TRUE.equals(expire)) {
            log.info("添加购物车 - 设置过期时间 - 失败 - cartKey={}", cartKey);
        }
    }

    /**
     * 获取购物车列表
     *
     * @param tokenUser 用户信息
     * @return 购物车列表
     */
    @Override
    public List<RespCartDto> getCart(TokenUser tokenUser) {
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        String userId = tokenUser.getUserId();
        String cartKey = RedisUtils.getKey(RedisUtils.CART_KEY, userId);
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        List<Object> values = hash.values(cartKey);
        List<RespCartDto> resp = Lists.newArrayList();
        for (Object value : values) {
            if (value instanceof String) {
                String redisJson = (String) value;
                RespCartDto respCartDto = JSONObject.parseObject(redisJson, RespCartDto.class);
                resp.add(respCartDto);
            }
        }
        List<RespCartDto> collect = resp.stream().sorted(Comparator.comparing(RespCartDto::getUpdateTime).reversed()).collect(Collectors.toList());
        for (RespCartDto respCartDto : collect) {
            List<RespCartCommodityListDto> cartCommodityList = respCartDto.getCartCommodityList();
            List<RespCartCommodityListDto> commodityCollection = cartCommodityList.stream()
                    .sorted(Comparator.comparing(RespCartCommodityListDto::getCommodityUpdateTime).reversed()).collect(Collectors.toList());
            respCartDto.setCartCommodityList(commodityCollection);
        }
        return collect;
    }

    /**
     * 清空购物车
     *
     * @param tokenUser 用户信息
     */
    @Override
    public void delCart(TokenUser tokenUser) {
        if (null == tokenUser || StringUtils.isBlank(tokenUser.getUserId())) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        //组装redis的key
        String cartKey = RedisUtils.getKey(RedisUtils.CART_KEY, tokenUser.getUserId());
        Boolean hasKey = stringRedisTemplate.hasKey(cartKey);
        if (Boolean.TRUE.equals(hasKey)) {
            List<Object> values = stringRedisTemplate.opsForHash().values(cartKey);
            log.info("清空购物车 - 购物车数据={}", JSONObject.toJSONString(values));
            Boolean delete = stringRedisTemplate.delete(cartKey);
            if (Boolean.FALSE.equals(delete)) {
                throw new BusinessException(BusinessEnum.FAIL);
            }
        }
    }
}
