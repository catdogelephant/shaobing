package com.zhumuchang.dongqu.controller.testcontroller;

import com.alibaba.fastjson.JSONObject;
import com.zhumuchang.dongqu.api.dto.commodity.CommodityDto;
import com.zhumuchang.dongqu.api.dto.testdto.EncryptDto;
import com.zhumuchang.dongqu.api.dto.testdto.EncryptUser;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.commons.annotation.Decrypt;
import com.zhumuchang.dongqu.commons.annotation.Encrypt;
import com.zhumuchang.dongqu.commons.annotation.PassToken;
import com.zhumuchang.dongqu.mapper.testmapper.TestMapper;
import com.zhumuchang.dongqu.service.impl.AsyncServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author sx
 * @Description 测试类控制器
 * @Date 2022/8/8 10:22
 */
@Slf4j
@RestController
@RequestMapping("/testController")
public class TestController {

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

    @Resource
    private ThreadPoolTaskExecutor threadPool;

    @Autowired
    private AsyncServiceImpl asyncServiceImpl;

    @GetMapping(name = "测试异步执行", path = "/testAsync")
    @PassToken
    public Object testAsync() {
//        String mainName = Thread.currentThread().getName();
//        log.info("测试异步执行 - 打印线程名 - mainName={}", mainName);
//        for (int i = 0; i < 500; i++) {
//            asyncServiceImpl.testAsyncMethod();
//        }

        for (int i = 0; i < 500; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String threadName = Thread.currentThread().getName();
                log.info("------------------->>> {} - {} <<<--------------------", threadName, finalI);
            });
        }
        log.info("测试异步执行 - 方法结束");
        return ResultDto.success();
    }

    @Autowired
    private TestMapper testMapper;

    @GetMapping(name = "测试mybatis加解密", path = "/testMybatisEncrypt")
    @PassToken
    public Object testMybatisEncrypt() {
        EncryptDto phone = new EncryptDto("13666666666");
        Integer integer = testMapper.updateEncryptUserPhone(1, phone);
        return integer;
//        SystemUserEncryptPhoneDto resp = testMapper.findSesameSystemUserDtoByPhone(phone);
//        return resp;
    }

    @PostMapping(name = "测试加密注解", path = "/testEncryptAn")
    @PassToken
    @Encrypt
    public Object testEncryptAn(@RequestBody EncryptUser param) {
        param.setName(null);
        param.setPhone("1333333333333");
        return param;
    }

    @PostMapping(name = "测试解密注解", path = "/testDecryptAn")
    @PassToken
    public Object testDecryptAn(@RequestBody @Decrypt EncryptUser param) {
        return param;
    }
}
