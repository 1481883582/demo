package com.redis_demo.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class deduc {

    @Autowired
    private RedissonClient redissonClient;

    public String deduc2() {
        String lockKey = "lock:pro-100";
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        // 锁续命
        try {
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
        return "ok";
    }
}
