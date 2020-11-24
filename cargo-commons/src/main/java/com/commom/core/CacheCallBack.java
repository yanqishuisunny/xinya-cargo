package com.commom.core;

import com.commom.exception.BussException;

public interface CacheCallBack<K, V> {

    /**
     * 获取数据，给缓存使用
     */
    V doGet(K k) throws BussException;

    /**
     * 初始化缓存数据
     * 当cache的lazy为false时调用
     */
    ResultMap<K,V> doInitialization(IKeyGenerator<K> generator) throws BussException;

    /**
     * 扩展自定义构造key结构
     */
    K doGenerate(K key);
}
