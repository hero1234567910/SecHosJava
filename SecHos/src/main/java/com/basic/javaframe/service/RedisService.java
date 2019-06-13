package com.basic.javaframe.service;

import java.util.List;
import java.util.Set;

public interface RedisService {
	
	 /**
     * 写入缓存
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
   boolean set( String key, Object value, long expireTime);
    /**
     * 批量删除对应的value
     * @param keys
     */
    void remove( String... keys);

    /**
     * 批量删除key
     * @param pattern
     */
    void removePattern( String pattern);
    /**
     * 删除对应的value
     * @param key
     */
    void remove( String key);
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    boolean exists(String key);
    /**
     * 读取缓存
     * @param key
     * @return
     */
   Object get(String key);
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    Object hmGet(String key, Object hashKey);

    /**
     * 列表添加
     * @param k
     * @param v
     */
    void lPush(String k,Object v);

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    List<Object> lRange(String k, long l, long l1);

    /**
     * 集合添加
     * @param key
     * @param value
     */
    void add(String key,Object value);

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
    public void zAdd(String key,Object value,double scoure);

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    Set<Object> rangeByScore(String key,double scoure,double scoure1);

    /**
     * 依据key 查询缓存时间
     * @param key
     * @return
     */
    long  getExpire(String key);


    /****
     * 设置缓存时间
     * @param key
     * @return
     */

    boolean expire(String key,long expireTime );
}
