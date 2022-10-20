package com.zhumuchang.dongqu.commons.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhumuchang.dongqu.api.service.order.SesameOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author sx
 * @Description 取消订单消费者
 * @Date 2022/10/20 10:40
 */
@Slf4j
@Component
public class CancelOrderConsume {

    @Autowired
    private SesameOrderService sesameOrderService;

    //监听的队列名
    @RabbitListener(queues = "cancelOrderQueue")
    public void cancelOrderReceive(Message message, Channel channel) {
        String msg = new String(message.getBody());
        List<Integer> list = JSONObject.parseObject(msg, List.class);
        sesameOrderService.queueCancelOrderByList(list);
        log.info(" 当前时间：{}, 收到死信队列信息{}", new Date().toString(), msg);
    }
}
