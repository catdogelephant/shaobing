package com.zhumuchang.dongqu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class MyController implements WebMvcConfigurer, HandlerInterceptor {

    /**
     *
     * @return 获取集合
     */
    @RequestMapping(name = "获取集合", path = "/getList")
    public Object getList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("@");
        list.add("3");
        return list;
    }

    @PostMapping(name = "/文件上传", path = "/upload")
    public String upload(@RequestParam("photos")MultipartFile[] multipartFile) {
//        log.info("文件上传 - 内容={}", JSONObject.toJSON(multipartFile));

        if (multipartFile.length == 0) {
            log.info("文件上传 - 内容为空");
            return "内容为空";
        }

        for (int i = 0; i < multipartFile.length; i++) {
            MultipartFile file = multipartFile[i];
            if (file.isEmpty()) {
                log.info("文件上传 - 内容为空 - 第{}个", i+1);
                return "第" + ++i + "个文件内容为空";
            }
            String originalFilename = file.getOriginalFilename();
            try {
                file.transferTo(new File("F://java//uploadFile//" + originalFilename));
            } catch (Exception e) {
                log.error("文件上传 - 异常=", e);
            }
        }
        return "success";
    }

    public static void main(String[] args) {
    }
}
