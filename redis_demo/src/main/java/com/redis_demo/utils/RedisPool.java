package com.redis_demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取Redis中list数据[反序列化]
 */
public class RedisPool {

    private static Jedis jedis;

    /**
     * 初始化
     * @return
     */
    public static Jedis getJedis() {

        if(jedis != null && jedis.isConnected()) return jedis;

        jedis = new JedisPool(
                new GenericObjectPoolConfig(),
                "127.0.0.1",
                6379,
                2000,
                null,
                0
        ).getResource();

        return jedis;
    }

    public static<T> List<T> getRedisList(String key, Class<T> t){
        List<T> receipt = new ArrayList<T>();

        List<String> list = getJedis().lrange(key, 0, -1);
        if(list.isEmpty()) return null;

        Jackson2JsonRedisSerializer<List<T>> redisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        for (int i = 0; i < list.size(); i++) {
            List<T> deserialize = redisSerializer.deserialize(list.get(i).getBytes());

            receipt.add(parseMap2Object((Map<String, Object>) deserialize.get(1), t));
        }

        return receipt;
    }


    /**
     * 将Map转换为对象
     * @param paramMap
     * @param cls
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }
}
