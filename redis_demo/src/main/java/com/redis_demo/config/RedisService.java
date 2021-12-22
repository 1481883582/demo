package com.redis_demo.config;

import java.util.List;
import java.util.Set;

public interface RedisService {

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value);

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    boolean set(final String key, Object value, Long expireTime);
    /**
     * 批量删除对应的value
     * @param keys
     */
    void remove(final String[] keys);
    /**
     * 批量删除key
     * @param pattern
     */
    void removePattern(final String pattern);
    /**
     * 删除对应的value
     * @param key
     */
    void remove(final String key);
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    boolean exists(final String key);
    /**
     * 读取缓存
     * @param key
     * @return
     */
    Object get(final String key);

    /**
     * 都
     * @param key
     * @return
     */
    Object getString(final String key);

    /**
     * 哈希 添加
     * @param key --
     * @param hashKey --
     * @param value --
     * @param expireTime --
     */
     void hmSet(String key, Object hashKey, Object value, Long expireTime);

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
     Object hmGet(String key, Object hashKey);

    /**
     * 清空列表
     * @param key
     */
    void removeList(String key);

    /**
     * 列表添加
     * @param k
     * @param v
     */
     void lPush(String k, Object v);

    /**
     * 根据范围，列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
     List<Object> lRange(String k, long l, long l1);

    /**
     * 全量获取列表
     *
     * @param k
     * @return
     */
     List<Object> getList(String k);

    /**
     * 集合添加
     * @param key
     * @param value
     */
     void add(String key, Object value);

    /**
     * 集合获取
     * @param key
     * @return
     */
     Set<Object> setMembers(String key);

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
     void zAdd(String key, Object value, double scoure);

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
     Set<Object> rangeByScore(String key, double scoure, double scoure1);

    /**
     * 正则表达式获取key
     * @param pattern
     * @return
     */
    Set<String> getKeyPattern(final String pattern);

    /**
     * redis 数据发布
     * @param channel -- 自定义信道
     * @param message -- 消息体
     */
    void convertAndSend(String channel, Object message);

    /**
     * 正则表达式删除key
     * @param pattern
     * @return
     */
    void removeKeyPattern(final String pattern);
}
