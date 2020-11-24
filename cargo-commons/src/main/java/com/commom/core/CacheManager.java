//package com.commom.core;
//
///**
// * 缓存管理器
// */
//public final class CacheManager {
//
//    /**
//     * 单例-实例
//     */
//    private static final CacheManager INSTANCE = new CacheManager();
//
//    /**
//     * 保存所有缓存实例
//     */
//    private final Map<String, ICache> uuidCaches = new ConcurrentHashMap<String, ICache>();
//
//    /**
//     * 禁止从外部拿到实例
//     */
//    private CacheManager() {
//    }
//
//    public static CacheManager getInstance() {
//        return INSTANCE;
//    }
//
//    /**
//     * 系统启动后自动注册所有的缓存类别
//     */
//    public <K,V> void registerCache(ICache<K, V> cache) {
//        // 不允许UUID重复，应用必须在实现的Cache接口确保命名不重复
//        String cacheId = cache.getCacheId();
//        if (uuidCaches.containsKey(cacheId)) {
//            throw new CacheDumplicateException(
//                    "uce-core-cache-CacheManager-registerCache：CacheId:[" + cacheId
//                            + "] to Class:[" + cache.getClass().getName()
//                            + "] and Class:["
//                            + uuidCaches.get(cacheId).getClass().getName()
//                            + "] dumplicated");
//        }
//        uuidCaches.put(cacheId, cache);
//    }
//
//    /**
//     * 根据uuid获取缓存实例
//     */
//    public <K,V> ICache<K, V> getCache(String cacheId) {
//        ICache<K, V> cache = uuidCaches.get(cacheId);
//        if (cache == null) {
//            throw new CacheNotFoundException("uce-core-cache-CacheManager-getCache：CacheId:["+cacheId+"] not found");
//        }
//        return cache;
//    }
//
//    /**
//     * 根据cacheId，目标Class获取缓存实例
//     */
//    public <T> T getCache(Class t,String cacheId) {
//        try {
//            return (T) getCache(cacheId);
//        } catch(ClassCastException e) {
//            throw new CacheNotFoundException(
//                    "uce-core-cache-CacheManager-getCache：CacheId:[" + cacheId
//                            + "] to Class:[" + t.getName() + "] not found");
//        }
//    }
//
//    /**
//     * 销毁
//     */
//    public void shutdown() {
//        uuidCaches.clear();
//    }
//}
