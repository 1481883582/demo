package com.redis_demo.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class RedissonController {
    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/lock")
    public String test(){
        String lockKey = "lock:pro-100";
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        // 锁续命
        try {
            Thread.sleep(100000L);
//            String productKey = "pro-100";
//            Integer count = (Integer) redisTemplate.opsForValue().get(productKey);
//            count--;
//            redisTemplate.opsForValue().set(productKey, count);
//            System.out.println("剩余库存：：" + count);
        } catch (Exception e) {
            System.out.println("系统异常");
        } finally {
            lock.unlock();
        }
        return "OK";
    }
}
