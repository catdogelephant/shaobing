package com.zhumuchang.dongqu.commons.mq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author sx
 * @Description 取消订单消费者
 * @Date 2022/10/20 10:40
 */
@Slf4j
@Component
public class CancelOrderConsume {

    //监听的队列名
    @RabbitListener(queues = "cancelOrderQueue")
    public void cancelOrderReceive(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info(" 当前时间：{}, 收到死信队列信息{}", new Date().toString(), msg);
    }
}
