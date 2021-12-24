package com.redis_demo.config.impl;

import com.redis_demo.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @Override
    public void removePattern(final String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys.size() > 0) {
            stringRedisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public Object getString(String key) {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        Object result = stringStringValueOperations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value, Long expireTime) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
        if (expireTime != null) {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }


    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 清空列表
     *
     * @param key
     */
    @Override
    public void removeList(String key) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPop(key);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    @Override
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 根据范围，列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @Override
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 全量获取列表
     * @param k
     * @return
     */
    @Override
    public List getList(String k) {
        BoundListOperations boundListOperations = redisTemplate.boundListOps(k);
        return boundListOperations.range(0, boundListOperations.size());
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    @Override
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    @Override
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    @Override
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @Override
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 正则表达式获取key
     *
     * @param pattern
     * @return
     */
    @Override
    public Set<String> getKeyPattern(final String pattern) {
        return this.stringRedisTemplate.keys(pattern);
    }

    /**
     * redis 数据发布
     *
     * @param channel -- 自定义信道
     * @param message -- 消息体
     */
    @Override
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    /**
     * 正则表达式删除key
     *
     * @param pattern
     * @return
     */
    @Override
    public void removeKeyPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 自增
     * @param key  key
     * @return
     */
    @Override
    public long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 自增加超时
     * @param key
     * @param expireTime
     * @return
     */
    @Override
    public long increment(String key, Long expireTime) {
        Long increment = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return increment;
    }


}
