package com.redis_demo;

import com.redis_demo.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class RedisDemoApplicationTests {

    @Resource
    private RedisService redisService;

    /**
     * 自增redis  || 设置原子过期时间
     */
    @Test
    void increment() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long increment = redisService.increment("123", 60L);
            log.info(increment + "");

            redisService.set("111", 111, 60L);
            redisService.set("222", 222);
        }
    }

    /**
     * redis 订阅发布
     */
    @Test
    void release() {

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisService.convertAndSend("cat", "布偶猫" + i);
        }


        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redisService.convertAndSend("dog", "哈士奇" + i);
        }

    }

    /**
     * setHash
     */
    @Test
    void setHash() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (; ; ) {
            long l = System.currentTimeMillis();
            redisService.hmSet("1", l, l, 60L);
            log.info(redisService.hmGet("1", l).toString());
        }
    }

}
