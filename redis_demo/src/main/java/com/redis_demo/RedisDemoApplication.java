package com.redis_demo;

import com.alibaba.fastjson.JSONObject;
import com.redis_demo.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootApplication
public class RedisDemoApplication implements ApplicationRunner {

    @Resource
    private RedisService redisService;

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisService.convertAndSend("cat", "布偶猫" + i);
            }
        }).start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisService.convertAndSend("dog", "哈士奇" + i);
            }
        }).start();

    }
}
