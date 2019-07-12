package com.jg.project.iface.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.jg.project.comm.exception.RocketMqException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * lora 设备订阅
 * @ClassName LoraMqConsumer
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 17:53
 * @Version 1.0
 **/
@SpringBootConfiguration
@Slf4j
public class LoraMqConsumer {

    @Value("${jg.rocketMq.consumer.ip}")
    private String ip;
    @Value("${jg.rocketMq.consumer.groupName}")
    private String groupName;
    @Value("${jg.rocketMq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${jg.rocketMq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${jg.rocketMq.topics}")
    private String topics;
    @Value("${jg.rocketMq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Autowired
    private LoraMqConsumerListener loraMqConsumerListener;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMqException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(ip);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(loraMqConsumerListener);
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        //如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        try {

            //订阅该topic下的全部 tag
            consumer.subscribe(topics,"*");
            consumer.start();
            log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,ip);
        }catch (MQClientException e){
            log.error("consumer start error!!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,ip,e);
            throw new RocketMqException(e);
        }
        return consumer;
    }
}
