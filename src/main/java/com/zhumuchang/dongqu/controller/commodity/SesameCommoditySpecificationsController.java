package com.zhumuchang.dongqu.controller.commodity;


import com.alibaba.fastjson.JSONObject;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqAddCommoditySpecificationsDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommoditySpecificationsService;
import com.zhumuchang.dongqu.commons.annotation.PassToken;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品规格 前端控制器
 * </p>
 *
 * @author sx
 * @since 2022-07-14
 */
@Slf4j
@RestController
@RequestMapping("/commodity/specifications")
public class SesameCommoditySpecificationsController {

    @Autowired
    private SesameCommoditySpecificationsService sesameCommoditySpecificationsService;

    @PostMapping(name = "设置商品规格", path = "/addCommoditySpecifications")
    public Object addCommoditySpecifications(HttpServletRequest request, @RequestBody ReqAddCommoditySpecificationsDto param) {
        TokenUser tokenUser = (TokenUser) request.getAttribute("tokenUser");
        sesameCommoditySpecificationsService.addCommoditySpecifications(tokenUser, param);
        return null;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Map<String, CommodityDto>> redisTemplate;

    @GetMapping(name = "测试redis", path = "/testRedis")
    @PassToken
    public void testRedis(String param) {
        String redisKey = "shaobing:cart:userId";
        //key=commodityId；value=commodityInfo
        Map<String, CommodityDto> map = new HashMap<>();
        CommodityDto one = new CommodityDto();
        one.setPrice(BigDecimal.ZERO);
        one.setCommodityName("第一个商品");
        CommodityDto two = new CommodityDto();
        two.setPrice(BigDecimal.ONE);
        two.setCommodityName("第二个商品");
        CommodityDto three = new CommodityDto();
        three.setPrice(BigDecimal.TEN);
        three.setCommodityName("第三个商品");
        map.put("1", one);
        map.put("2", two);
        map.put("3", three);
        if ("1".equals(param)) {
            redisTemplate.opsForHash().putAll(redisKey, map);
        }
        List<Object> values = redisTemplate.opsForHash().values(redisKey);
        for (Object value : values) {
            if (value instanceof Map) {
                Map<String, CommodityDto> valueMap = (Map<String, CommodityDto>) value;
                for (String key : valueMap.keySet()) {
                    System.out.println("==================== key = " + key + "====================");
                    System.out.println("==================== value = " + JSONObject.toJSONString(valueMap.get(key)) + "====================");
                }
            }
        }
    }
}
