package com.zhumuchang.dongqu.controller.testcontroller;

import com.zhumuchang.dongqu.commons.annotation.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author sx
 * @Description rabbitmq测试控制器
 * @Date 2022/10/20 10:19
 */
@Slf4j
@RestController
@RequestMapping("/testRabbitMq")
public class RabbitMqTestController {

    /**
     * 操作rabbitmq
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //http://localhost:8080/shaobing/testRabbitMq/deadQueue/哈哈/5000
    @PassToken
    @GetMapping(name = "测试死信队列", path = "/deadQueue/{message}/{ttltime}")
    public void deadQueue(@PathVariable String message, @PathVariable String ttltime) {
        log.info(" 当前时间：{}, 发送一条时长{} 毫秒 TTL  信息给队列 C:{}", new Date(), ttltime, message);
        rabbitTemplate.convertAndSend("normalExchange", "normalKey", message, msg -> {
            msg.getMessageProperties().setExpiration(ttltime);
            return msg;
        });
    }
}
