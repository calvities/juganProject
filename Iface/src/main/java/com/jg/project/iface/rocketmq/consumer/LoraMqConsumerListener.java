package com.jg.project.iface.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.jg.project.service.gw.GwLogServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * lora 设备 消息监听
 *
 * @ClassName LoraMqConsumerListener
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 17:56
 * @Version 1.0
 **/
@Component
@SpringBootConfiguration
@Slf4j
public class LoraMqConsumerListener implements MessageListenerConcurrently {

    @Autowired
    private GwLogServiceI gwLogService;

    @Value("${jg.rocketMq.topics}")
    private String topic;
    @Value("${jg.rocketMq.tag.login}")
    private String login;
    @Value("${jg.rocketMq.tag.logout}")
    private String logout;
    @Value("${jg.rocketMq.tag.will}")
    private String will;
    @Value("${jg.rocketMq.tag.state}")
    private String state;
    @Value("${jg.rocketMq.tag.status}")
    private String status;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        if (CollectionUtils.isEmpty(msgs)) {

            log.info("接收到的消息为空，不做任何处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        MessageExt messageExt = msgs.get(0);
        String msg = new String(messageExt.getBody());
        log.info("接收到的消息是:{}", msg);

        if (messageExt.getTopic().equals(topic)) {
            if (messageExt.getTags().equals(login) ||
                    messageExt.getTags().equals(logout)) {
                int tryTimes = messageExt.getReconsumeTimes();
                if (tryTimes == 3) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                gwLogService.insert(msg);
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
