package com.wong.kmall.common.service;

import java.util.concurrent.TimeUnit;

/**
 * @author KaKinWong
 * @crate_time 2021/7/27 23:18
 * @description Redis操作Service接口
 */
public interface RedisService {
    /**
     * 存储数据
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置超时时间（单位秒）
     *
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 设置超时时间
     *
     * @param key
     * @param expire
     * @param timeUnit
     * @return
     */
    boolean expire(String key, long expire, TimeUnit timeUnit);

    /**
     * 删除数据
     *
     * @param key
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param key
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);
}
