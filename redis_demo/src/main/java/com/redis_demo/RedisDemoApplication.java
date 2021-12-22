package com.redis_demo;

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
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisService.convertAndSend("test", "你好");
                log.info("发送了。。。。");
            }
        }).start();

    }
}
