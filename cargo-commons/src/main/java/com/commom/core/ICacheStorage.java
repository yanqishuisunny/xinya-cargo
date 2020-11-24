package com.commom.core;

import java.util.Map;

public interface ICacheStorage<K, V> {

    /**
     * 将value关联到key
     */
    boolean set(K key, V value);

    /**
     * 将value关联到key,并将key的生存时间设置为expire（以秒为单位）
     */
    boolean set(K key, V value, int expire);

    /**
     * 返回key所关联的value
     */
    V get(K key);

    /**
     * 删除给定key
     */
    void remove(K key);

    /**
     * 删除给定的一个活多个key
     */
    @SuppressWarnings("unchecked")
    void removeMulti(K ... keys);

    /**
     * 将map放入缓存，使用map中的key作为键，value做为值
     */
    boolean set(Map<K, V> values);

    /**
     * 将map放入缓存，使用map中的key作为键，value做为值，map中每个key的生存时间为expire（以秒为单位）
     */
    boolean set(Map<K, V> values, int expire);

    /**
     * 返回同类缓存下所有数据
     */
    Map<K, V> get(Context context);

    /**
     * 清除同类缓存类型下所有数据
     */
    void clear(Context context);

    /**
     * 查询key的生存时间
     */
    long ttl(K key);

}

