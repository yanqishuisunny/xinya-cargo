package com.commom.core;

import java.util.Map;

public interface ICache<K, V> {

    /**
     * 用户在使用缓存的时候，都会重写一个方法，来标记当前Cache的UUID，这个UUID也是数据缓存的key。
     * 因此，接口中一定有一个getCacheId()的方法。
     */
    String getCacheId();

    /**
     * 用户可以通过key，获取缓存中的value。
     */
    V get(K key);

    /**
     * 用户可以通过key，获取缓存中的value。bIsLoad=true的时候，缓存获取为空时不回调doGet
     */
    V get(K key,boolean bIsLoad);

    /**
     * 一次性取出缓存上的所有内容
     */
    Map<K,V> get();

    /**
     * 失效一组缓存
     */
    void invalid();

    /**
     * 失效key对应的缓存
     */
    void invalid(K key);

    /**
     * 失效传入的多个key对应的缓存
     */
    void invalidMulti(K ... keys);

    boolean refresh(K key);

    long ttl(K key);
}

