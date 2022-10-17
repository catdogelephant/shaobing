package com.zhumuchang.dongqu.service.impl.order;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhumuchang.dongqu.api.bean.order.SesameOrder;
import com.zhumuchang.dongqu.api.bean.order.SesameOrderCommodity;
import com.zhumuchang.dongqu.api.dto.commodity.SpecificationsDto;
import com.zhumuchang.dongqu.api.dto.order.other.AddressDto;
import com.zhumuchang.dongqu.api.dto.order.other.OrderCommodityJsonDto;
import com.zhumuchang.dongqu.api.dto.order.other.OrderSpeJsonDto;
import com.zhumuchang.dongqu.api.dto.order.req.*;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartCommodityListDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespCartDto;
import com.zhumuchang.dongqu.api.dto.order.resp.RespConfirmOrderDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityService;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommoditySpecificationsService;
import com.zhumuchang.dongqu.api.service.order.SesameAddressService;
import com.zhumuchang.dongqu.api.service.order.SesameOrderCommodityService;
import com.zhumuchang.dongqu.api.service.order.SesameOrderService;
import com.zhumuchang.dongqu.api.service.shop.SesameShopService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.enumapi.OrderStatusEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.commons.utils.RedisUtils;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommoditySpecificationsMapper;
import com.zhumuchang.dongqu.mapper.order.SesameOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private SesameOrderMapper sesameOrderMapper;

    @Autowired
    private SesameCommoditySpecificationsService sesameCommoditySpecificationsService;

    @Autowired
    private SesameCommoditySpecificationsMapper specificationsMapper;

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
     * 收货地址
     */
    @Autowired
    private SesameAddressService sesameAddressService;

    /**
     * 订单商品
     */
    @Autowired
    private SesameOrderCommodityService sesameOrderCommodityService;

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
            commodityListDto.setSpecificationsThumbnail(specificationsDto.getThumbnail());
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

    /**
     * 确认订单页
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     * @return 订单页商品列表
     */
    @Override
    public RespConfirmOrderDto confirmOrder(TokenUser tokenUser, List<ReqConfirmOrderDto> param) {
        if (null == tokenUser || null == param || param.isEmpty()) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        //获取规格id集合
        List<String> specificationsOpenIdList = Lists.newArrayList();
        for (ReqConfirmOrderDto dto : param) {
            List<String> speList = dto.getSpeOpenIdList();
            specificationsOpenIdList.addAll(speList);
        }
        //获取店铺id集合
        List<Object> shopOpenIdList = param.stream().map(ReqConfirmOrderDto::getShopOpenId).collect(Collectors.toList());
        //获取redis中对应的店铺数据
        String cartKey = RedisUtils.getKey(RedisUtils.CART_KEY, tokenUser.getUserId());
        List<Object> objectList = stringRedisTemplate.opsForHash().multiGet(cartKey, shopOpenIdList);
        List<RespCartDto> respShopList = Lists.newArrayList();
        Integer totalNum = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Object value : objectList) {
            if (value instanceof String) {
                String redisJson = (String) value;
                RespCartDto respCartDto = JSONObject.parseObject(redisJson, RespCartDto.class);
                List<RespCartCommodityListDto> cartCommodityList = respCartDto.getCartCommodityList();
                Boolean createShopFlag = Boolean.TRUE;
                RespCartDto shopDto = new RespCartDto();
                List<RespCartCommodityListDto> respCommodityList = Lists.newArrayList();
                shopDto.setCartCommodityList(respCommodityList);
                for (RespCartCommodityListDto commodityDto : cartCommodityList) {
                    if (specificationsOpenIdList.contains(commodityDto.getSpecificationsOpenId())) {
                        if (createShopFlag) {
                            shopDto.setShopName(respCartDto.getShopName());
                            createShopFlag = Boolean.FALSE;
                        }
                        respCommodityList.add(commodityDto);
                        totalNum += commodityDto.getCommodityNum();
                        BigDecimal commodityPrice = commodityDto.getCommodityPrice().multiply(new BigDecimal(String.valueOf(commodityDto.getCommodityNum())));
                        totalPrice = totalPrice.add(commodityPrice);
                    }
                }
                if (Boolean.FALSE.equals(createShopFlag)) {
                    respShopList.add(shopDto);
                }
            }
        }
        RespConfirmOrderDto resp = new RespConfirmOrderDto();
        resp.setTotalNum(totalNum);
        resp.setTotalPrice(totalPrice);
        resp.setPayPrice(totalPrice);
        resp.setFavorablePrice(totalPrice.subtract(totalPrice));
        resp.setShopList(respShopList);
        return resp;
    }

    /**
     * 创建订单
     *
     * @param tokenUser 用户信息
     * @param param     请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(TokenUser tokenUser, ReqCreateOrderDto param) {
        if (null == tokenUser) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "用户信息为空");
        }
        List<ReqCreateOrderSpeDto> speList = param.getSpeList();
        List<ReqCreateOrderSpeNumDto> allSpeNumList = new ArrayList<>();
        for (ReqCreateOrderSpeDto speDto : speList) {
            List<ReqCreateOrderSpeNumDto> speNumList = speDto.getSpeNumList();
            if (speNumList.isEmpty()) {
                log.info("创建订单 - 校验商品集合为空 - param={}", JSONObject.toJSONString(param));
                throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品信息为空，请稍后重试");
            }
            for (ReqCreateOrderSpeNumDto speNumDto : speNumList) {
                if (StringUtils.isBlank(speNumDto.getSpecificationsOpenId())) {
                    log.info("创建订单 - 校验商品集合 - 规格ID为空 - speNumList={}", JSONObject.toJSONString(speNumList));
                    throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品信息为空，请稍后重试");
                }
                if (null == speNumDto.getNum() || ConstantsUtils.CODE_1.compareTo(speNumDto.getNum()) > 0 || ConstantsUtils.CODE_99.compareTo(speNumDto.getNum()) < 0) {
                    log.info("创建订单 - 校验商品集合 - 数量异常 - speNumList={}", JSONObject.toJSONString(speNumList));
                    throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL.getCode(), "商品数量错误，请稍后重试");
                }
            }
            allSpeNumList.addAll(speNumList);
        }
        //获取收货地址信息
        AddressDto addressDto = sesameAddressService.getDtoByOpenIdAndUserId(param.getAddressOpenId(), tokenUser.getUserId());
        String consigneeAddress = String.join(ConstantsUtils.COMMA, addressDto.getProvince(), addressDto.getCity(), addressDto.getArea(),
                addressDto.getStreet(), addressDto.getDetailedAddress());
        //根据店铺ID分组
//        speList.stream().collect(Collectors.groupingBy());
        //设置商品信息
        String speOpenIds = allSpeNumList.stream().map(ReqCreateOrderSpeNumDto::getSpecificationsOpenId).collect(Collectors.joining(ConstantsUtils.COMMA));
        List<OrderCommodityJsonDto> orderCommodityList = specificationsMapper.listOrderCommodity(speOpenIds);
        if (null == orderCommodityList || orderCommodityList.isEmpty()) {
            log.info("创建订单 - 查询商品规格为空 - speOpenIds={}", speOpenIds);
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "商品信息错误，请刷新后重试");
        }
        int orderCommoditySpeSize = 0;
        for (OrderCommodityJsonDto dto : orderCommodityList) {
            List<OrderSpeJsonDto> orderSpeList = dto.getOrderSpeList();
            orderCommoditySpeSize = orderCommoditySpeSize + orderSpeList.size();
            for (OrderSpeJsonDto orderSpe : orderSpeList) {
                for (ReqCreateOrderSpeNumDto allSpeNum : allSpeNumList) {
                    if (orderSpe.getSpecificationsOpenId().equals(allSpeNum.getSpecificationsOpenId())) {
                        orderSpe.setCommodityNum(allSpeNum.getNum());
                        break;
                    }
                }
            }
        }
        if (allSpeNumList.size() != orderCommoditySpeSize) {
            log.info("创建订单 - 查询商品规格数量缺少 - speOpenIdList.size={}, orderCommoditySpeSize={}", orderCommoditySpeSize, orderCommodityList.size());
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND.getCode(), "商品信息错误，请刷新后重试");
        }
        List<SesameOrderCommodity> insertSesameOrderCommodityList = new ArrayList<>();
        //拆单
        for (OrderCommodityJsonDto commodityDto : orderCommodityList) {
            String orderCommodityJson = JSONObject.toJSONString(commodityDto);
            List<OrderSpeJsonDto> orderSpeList = commodityDto.getOrderSpeList();
            List<String> speOpenIdContainsList = orderSpeList.stream().map(OrderSpeJsonDto::getSpecificationsOpenId).collect(Collectors.toList());
            //设置总价
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderSpeJsonDto totalPriceDto : orderSpeList) {
                totalPrice = totalPrice.add(totalPriceDto.getSpecificationsPrice().multiply(new BigDecimal(String.valueOf(totalPriceDto.getCommodityNum()))));
            }
            //设置备注
            String remarks = null;
            for (ReqCreateOrderSpeDto speDto : speList) {
                List<ReqCreateOrderSpeNumDto> speNumList = speDto.getSpeNumList();
                ReqCreateOrderSpeNumDto speNumDto = speNumList.get(0);
                if (speOpenIdContainsList.contains(speNumDto.getSpecificationsOpenId())) {
                    remarks = speDto.getRemarks();
                    break;
                }
            }
            //设置订单信息
            int random = (int) ((Math.random() * 9 + 1) * 10000000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(new Date());
            String orderNo = "SES" + format + random;
            SesameOrder sesameOrder = this.createBean(IdUtil.simpleUUID(), Integer.valueOf(tokenUser.getUserId()), orderNo, null, null,
                    totalPrice, totalPrice, BigDecimal.ZERO, null, addressDto.getConsigneeName(), addressDto.getConsigneePhone(), addressDto.getProvince(),
                    addressDto.getCity(), addressDto.getArea(), addressDto.getStreet(), addressDto.getDetailedAddress(), consigneeAddress, commodityDto.getShopId(),
                    commodityDto.getShopOpenId(), commodityDto.getShopName(), orderCommodityJson, remarks, OrderStatusEnum.TO_BE_PAID.getCode(), null,
                    null, 1, Integer.valueOf(tokenUser.getUserId()), tokenUser.getUserName(), LocalDateTime.now(),
                    Integer.valueOf(tokenUser.getUserId()), tokenUser.getUserName(), null);
            Integer insert = sesameOrderMapper.saveRetureId(sesameOrder);
            if (null == insert || insert != 1) {
                log.info("创建订单 - 保存订单失败 - sesameOrder={}", JSONObject.toJSONString(sesameOrder));
                throw new BusinessException(BusinessEnum.FAIL.getCode(), "创建订单失败");
            }
            //设置订单商品信息
            for (OrderSpeJsonDto orderSpe : orderSpeList) {
                SesameOrderCommodity sesameOrderCommodity = this.createSesameOrderCommodity(IdUtil.simpleUUID(), sesameOrder.getId(), sesameOrder.getOrderNo(),
                        commodityDto.getShopId(), commodityDto.getShopOpenId(), commodityDto.getShopName(), orderSpe.getCommodityId(), orderSpe.getCommodityOpenId(),
                        orderSpe.getCommodityName(), orderSpe.getSpecificationsId(), orderSpe.getSpecificationsOpenId(), orderSpe.getSpecificationsName(),
                        orderSpe.getSpecificationsPrice(), null, orderSpe.getCommodityNum(), 1, Integer.valueOf(tokenUser.getUserId()),
                        tokenUser.getUserName(), LocalDateTime.now(), Integer.valueOf(tokenUser.getUserId()), tokenUser.getUserName(), null);
                insertSesameOrderCommodityList.add(sesameOrderCommodity);
            }
        }
        if (!insertSesameOrderCommodityList.isEmpty()) {
            boolean flag = sesameOrderCommodityService.saveBatch(insertSesameOrderCommodityList);
            if (!flag) {
                log.info("创建订单 - 保存订单商品集合失败 - insertSesameOrderCommodityList={}", JSONObject.toJSONString(insertSesameOrderCommodityList));
                throw new BusinessException(BusinessEnum.FAIL.getCode(), "创建订单失败");
            }
        }
    }

    public SesameOrder createBean(String openId, Integer userId, String orderNo, String transactionNo, Integer payType, BigDecimal totalPrice,
                                  BigDecimal payPrice, BigDecimal favorablePrice, BigDecimal discount, String consigneeName, String consigneePhone,
                                  String province, String city, String area, String street, String detailedAddress, String consigneeAddress,
                                  Integer shopId, String shopOpenId, String shopName, String commodityJson, String remarks, Integer status,
                                  LocalDateTime payTime, LocalDateTime deliveryTime, Integer delFlag, Integer createdId, String createdName,
                                  LocalDateTime createdTime, Integer updatedId, String updatedName, LocalDateTime updatedTime) {
        SesameOrder sesameOrder = new SesameOrder();
        sesameOrder.setOpenId(openId);
        sesameOrder.setUserId(userId);
        sesameOrder.setOrderNo(orderNo);
        sesameOrder.setTransactionNo(transactionNo);
        sesameOrder.setPayType(payType);
        sesameOrder.setTotalPrice(totalPrice);
        sesameOrder.setPayPrice(payPrice);
        sesameOrder.setFavorablePrice(favorablePrice);
        sesameOrder.setDiscount(discount);
        sesameOrder.setConsigneeName(consigneeName);
        sesameOrder.setConsigneePhone(consigneePhone);
        sesameOrder.setConsigneeProvince(province);
        sesameOrder.setConsigneeCity(city);
        sesameOrder.setConsigneeArea(area);
        sesameOrder.setConsigneeStreet(street);
        sesameOrder.setConsigneeDetailedAddress(detailedAddress);
        sesameOrder.setConsigneeAddress(consigneeAddress);
        sesameOrder.setSesameShopId(shopId);
        sesameOrder.setSesameShopOpenId(shopOpenId);
        sesameOrder.setSesameShopName(shopName);
        sesameOrder.setCommodityJson(commodityJson);
        sesameOrder.setRemarks(remarks);
        sesameOrder.setStatus(status);
        sesameOrder.setPayTime(payTime);
        sesameOrder.setDeliveryTime(deliveryTime);
        sesameOrder.setDelFlag(delFlag);
        sesameOrder.setCreatedId(createdId);
        sesameOrder.setCreatedName(createdName);
        sesameOrder.setCreatedTime(createdTime);
        sesameOrder.setUpdatedId(updatedId);
        sesameOrder.setUpdatedName(updatedName);
        sesameOrder.setUpdatedTime(updatedTime);
        return sesameOrder;
    }

    public SesameOrderCommodity createSesameOrderCommodity(String openId, Integer sesameOrderId, String sesameOrderNo, Integer sesameShopId, String sesameShopOpenId,
                                                           String sesameShopName, Integer sesameCommodityId, String sesameCommodityOpenId,
                                                           String sesameCommodityName, Integer sesameSpecificationsId, String sesameSpecificationsOpenId,
                                                           String sesameSpecificationsName, BigDecimal originalPrice, BigDecimal payPrice, Integer num,
                                                           Integer delFlag, Integer createdId, String createdName, LocalDateTime createdTime,
                                                           Integer updatedId, String updatedName, LocalDateTime updatedTime) {
        SesameOrderCommodity sesameOrderCommodity = new SesameOrderCommodity();
        sesameOrderCommodity.setOpenId(openId);
        sesameOrderCommodity.setSesameOrderId(sesameOrderId);
        sesameOrderCommodity.setSesameOrderNo(sesameOrderNo);
        sesameOrderCommodity.setSesameShopId(sesameShopId);
        sesameOrderCommodity.setSesameShopOpenId(sesameShopOpenId);
        sesameOrderCommodity.setSesameShopName(sesameShopName);
        sesameOrderCommodity.setSesameCommodityId(sesameCommodityId);
        sesameOrderCommodity.setSesameCommodityOpenId(sesameCommodityOpenId);
        sesameOrderCommodity.setSesameCommodityName(sesameCommodityName);
        sesameOrderCommodity.setSesameSpecificationsId(sesameSpecificationsId);
        sesameOrderCommodity.setSesameSpecificationsOpenId(sesameSpecificationsOpenId);
        sesameOrderCommodity.setSesameSpecificationsName(sesameSpecificationsName);
        sesameOrderCommodity.setOriginalPrice(originalPrice);
        sesameOrderCommodity.setPayPrice(payPrice);
        sesameOrderCommodity.setNum(num);
        sesameOrderCommodity.setDelFlag(delFlag);
        sesameOrderCommodity.setCreatedId(createdId);
        sesameOrderCommodity.setCreatedName(createdName);
        sesameOrderCommodity.setCreatedTime(createdTime);
        sesameOrderCommodity.setUpdatedId(updatedId);
        sesameOrderCommodity.setUpdatedName(updatedName);
        sesameOrderCommodity.setUpdatedTime(updatedTime);
        return sesameOrderCommodity;
    }
//    @Override
//    public RespConfirmOrderDto confirmOrder(TokenUser tokenUser, List<ReqConfirmOrderDto> param) {
//        if (null == tokenUser || null == param || param.isEmpty()) {
//            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
//        }
//        //获取规格集合
//        List<ConfirmOrderShopListDto> specificationsDtoList = specificationsMapper.getDtoByOpenId(param);
//        Integer totalNum = 0;
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        //设置单个商品数量
//        for (ConfirmOrderShopListDto respDto : specificationsDtoList) {
//            List<ConfirmOrderCommodityListDto> commodityList = respDto.getCommodityList();
//            for (ConfirmOrderCommodityListDto commodity : commodityList) {
//                for (ReqConfirmOrderDto requestParam : param) {
//                    if (commodity.getSpecificationsOpenId().equals(requestParam.getSpecificationsOpenId())) {
//                        commodity.setCommodityNum(requestParam.getNum());
//                        totalNum += requestParam.getNum();
//                        totalPrice = totalPrice.add(commodity.getCommodityPrice());
//                        break;
//                    }
//                }
//            }
//        }
//        RespConfirmOrderDto resp = new RespConfirmOrderDto();
//        //设置商品总数量
//        resp.setTotalNum(totalNum);
//        resp.setTotalPrice(totalPrice);
//        resp.setPayPrice(totalPrice);
//        resp.setFavorablePrice(totalPrice.subtract(totalPrice));
//        resp.setShopList(specificationsDtoList);
//        return resp;
//    }

    public static void main(String[] args) {
        String times = "1663151544000";
        long time = Long.parseLong(times);
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime timeLocal = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(timeLocal);
        System.out.println(timeLocal.getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String timeFormat = formatter.format(timeLocal);
        System.out.println(timeFormat);
        String nowFormat = formatter.format(LocalDateTime.now());
        System.out.println(nowFormat);
        System.out.println(nowFormat.equals(timeFormat));
    }
}
