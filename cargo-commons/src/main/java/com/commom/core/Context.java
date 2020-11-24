package com.commom.core;

public class Context {

    /**
     * 缓存对象
     */
    private ICache<?, ?> cache;

    /**
     * 构造器
     */
    public Context(ICache<?, ?> cache) {
        this.cache = cache;
    }

    /**
     * 获取缓存id
     */
    public String getCacheId() {
        return this.cache.getCacheId();
    }

}