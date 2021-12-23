package com.redis_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CatMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("CatMessageListener 消息内容:" + new String(message.getBody(), StandardCharsets.UTF_8));
        log.info("CatMessageListener 通道:" + new String(message.getChannel(), StandardCharsets.UTF_8));
    }
}
