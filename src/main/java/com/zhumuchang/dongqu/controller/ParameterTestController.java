package com.zhumuchang.dongqu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * @Author sx
 * @Description 路径参数测试控制器
 * @Date 2022/3/6 8:39
 */
@RestController
@Slf4j
public class ParameterTestController {

    @PostMapping(name = "路径参数测试接口", path = "/pathVa/{id}/onwer/{userName}")
    public Map<String, Object> pathVaTest(@PathVariable("id") String carId,
                                          @PathVariable("userName") String uName,
                                          @PathVariable Map<String, String> map) {
        log.info("路径参数测试接口 - /pathVa/{id}/onwer/{userName} - id={}, userName={}", carId, uName);
        Map<String, Object> resp = new HashMap<>();
        resp.put("carId", carId);
        resp.put("userName", uName);
        resp.put("map", map);
        return resp;
    }

    @PostMapping(name = "获取请求头", path = "/headers/{id}/onwer/{userName}")
    public Map<String, Object> headersTest(@RequestHeader("user-agent") String userAgent,
                                           @RequestHeader Map<String, String> map) {
        log.info("获取请求头 - /headers/{id}/onwer/{userName}");
        Map<String, Object> resp = new HashMap<>();
        resp.put("userAgent", userAgent);
        resp.put("map", map);
        return resp;
    }

    @PostMapping(name = "获取请求参数", path = "/param/{id}/onwer/{userName}")
    public Map<String, Object> paramTest(@PathVariable("id") String carId,
                                         @PathVariable("userName") String uName,
                                         @RequestParam("age") Integer age,
                                         @RequestParam("list") String strList,
                                         @RequestParam("list") List<String> list,
                                         @RequestParam Map<String, String> map,
                                         @RequestParam("intt") Integer intt) {
        log.info("获取请求参数 - /param/{id}/onwer/{userName}");
        Map<String, Object> resp = new HashMap<>();
        resp.put("carId", carId);
        resp.put("userName", uName);
        resp.put("age", age);
        resp.put("strList", strList);
        resp.put("list", list);
        resp.put("map", map);
        resp.put("intt", intt);
        return resp;
    }

    @PostMapping(name = "获取cookie", path = "/cookie/{id}/onwer/{userName}")
    public Map<String, Object> headersTest(@CookieValue("_ga") String ga,
                                           @CookieValue("_ga") Cookie cookie) {
        log.info("获取请求头 - /cookie/{id}/onwer/{userName}");
        Map<String, Object> resp = new HashMap<>();
        resp.put("ga", ga);
        System.out.println(cookie.getName() + "======>" + cookie.getValue());
        return resp;
    }

    // 请求：/cars/sell;low=34;brand=byd,audi/boss;low=11;brand=boosBrand
//    @GetMapping(name = "矩阵变量", path = "/cars/sell") 这样写会提示404
    @GetMapping(name = "矩阵变量", path = "/cars/{path}/{path2}")
    public synchronized Map<String, Object> juzhenParamTest(@MatrixVariable(value = "low", pathVar = "path") Integer low,
                                               @MatrixVariable(value = "brand", pathVar = "path") List<String> brand,
                                               @MatrixVariable(value = "low", pathVar = "path2") Integer low2,
                                               @MatrixVariable(value = "brand", pathVar = "path2") List<String> brand2,
                                               @PathVariable("path") String path,
                                               @PathVariable("path2") String path2) {
        log.info("矩阵变量 - /cars/{path}");
        Map<String, Object> resp = new HashMap<>();
        resp.put("low", low);
        resp.put("brand", brand);
        resp.put("path", path);
        resp.put("low2", low2);
        resp.put("brand2", brand2);
        resp.put("path2", path2);
        return resp;
    }

    public static void main(String[] args) {
        String str = new String("abc");
        Object obj = (Object)str;
        System.out.println(str == obj);
    }
}
