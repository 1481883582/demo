package com.redis_demo.config;

import com.redis_demo.MyMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * redis 消息订阅者
 *
 * @author daidasheng
 */
@Slf4j
@Configuration
public class RedisSubcribe {
    @Resource
    private MyMessageListener redisSubscribeService;

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisSubscribeService, "onMessage");
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new PatternTopic("test"));
        return container;
    }
}
