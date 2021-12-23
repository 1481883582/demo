package com.redis_demo.config;

import com.redis_demo.CatMessageListener;
import com.redis_demo.DogMessageListener;
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
    private CatMessageListener catMessageListener;

    @Resource
    private DogMessageListener dogMessageListener;

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);

        container.addMessageListener(new MessageListenerAdapter(catMessageListener, "onMessage"),
                new PatternTopic("cat"));

        container.addMessageListener(new MessageListenerAdapter(dogMessageListener, "onMessage"),
                new PatternTopic("cat"));

        return container;
    }
}
