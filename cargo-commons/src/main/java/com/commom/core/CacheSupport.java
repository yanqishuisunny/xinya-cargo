//package com.commom.core;
//
//import java.util.Map;
//
//import com.commom.exception.BussException;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.util.StringUtils;
//
//
//public abstract class CacheSupport<V> extends BaseCache<String, V> implements CacheCallBack<String, V>, IKeyGenerator<String> {
//
//    protected final Log LOG = LogFactory.getLog(CacheSupport.class);
//
//    private ICacheStorage<String, V> storage = null;
//
//    /**
//     * 超时时间，单位：秒，无默认值；默认数据是不超时的
//     */
//    private int timeOut;
//
//    /**
//     * 数据放入缓存的时候，是否设置数据过期时间
//     */
//    private boolean enableTimeOut = false;
//
//    /**
//     * 设置数据超时时间
//     */
//    public void setTimeOut(int timeOut) {
//        enableTimeOut = timeOut > 0 ? true : false;
//        this.timeOut = timeOut;
//    }
//
//    /**
//     * 延迟加载作用：容许用户预先加载一波数据到缓存上，数据来自于doInitialization()
//     * */
//    private boolean lazy = true;
//
//    /**
//     * 设置是否延迟加载缓存数据
//     */
//    public void setLazy(boolean lazy) {
//        this.lazy = lazy;
//    }
//
//    /**
//     * 设置缓存存储器，参考：集中存储器{@link CentralizedStorage}和本地存储器{@link LocallyStorage}
//     */
//    public void setStorage(ICacheStorage<String, V> storage) {
//        this.storage = storage;
//    }
//
//    protected ICacheStorage<String, V> getStorage() {
//        return storage;
//    }
//
//    /**
//     * 初始化方法
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//        super.afterPropertiesSet();
//
//        CacheManager.getInstance().registerCache(this);
//
//        if(!this.lazy) {
//            ResultMap<String, V> cacheData = initialization();
//            if(this.enableTimeOut) {
//                this.storage.set(cacheData,timeOut);
//            } else {
//                this.storage.set(cacheData);
//            }
//        }
//    }
//
//    /**
//     * 获取缓存
//     * 注：这里捕获的异常，都是redisStorage中抛出来的；
//     * 	  locallyStorage抛出的是CacheNotFoundException异常。
//     */
//    @Override
//    public V get(String key) {
//        if(StringUtils.isEmpty(key)) {
//            throw new RedisCacheStorageException("uce-core-cache-CacheSupport：key不允许为null或空串!");
//        }
//        V value = null;
//        String newKey = generate(key);
//        try {
//            value = storage.get(newKey);
//        } catch (CacheNotFoundException lex) {//LocallyStorage key 不存在
//            value = execute(key);
//            LOG.info("uce-core-cache-CacheSupport：本地缓存["+getCacheId()+"]，key["+key+"]不存在，走数据库查询，返回结果["+value+"]");
//
//            if(enableTimeOut) {
//                storage.set(newKey, value, timeOut);
//            } else {
//                storage.set(newKey, value);
//            }
//        } catch(ValueIsBlankException e) {
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]存在，value为空串，返回结果[null]");
//            //key存在，value为空串
//            return null;
//        } catch(ValueIsNullException ex) {
//            //key存在，value为null
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]存在，value为null，返回结果[null]");
//            return null;
//        } catch(KeyIsNotFoundException ex1) {//RedisStorage key 不存在
//            //key不存在
//            value = execute(key);
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]不存在，走数据库查询，返回结果["+value+"]");
//
//            if(enableTimeOut) {
//                storage.set(newKey, value, timeOut);
//            } else {
//                storage.set(newKey, value);
//            }
//        } catch(RedisConnectionException exx) {
//            //redis连接出现异常
//            value = execute(key);
//            LOG.info("uce-core-cache-CacheSupport：Redis连接出现异常，走数据库查询!");
//            return value;
//        }
//        return value;
//    }
//
//    /**
//     * 获取缓存
//     * 注：这里捕获的异常，都是redisStorage中抛出来的；
//     * 	  locallyStorage抛出的是CacheNotFoundException异常。
//     */
//    @Override
//    public V get(String key,boolean bIsLoad) {
//        if(StringUtils.isEmpty(key)) {
//            throw new RuntimeException("uce-core-cache-CacheSupport：key不允许为null或空串!");
//        }
//        V value = null;
//        String newKey = generate(key);
//        try {
//            value = storage.get(newKey);
//        } catch (CacheNotFoundException lex) {
//            if(!bIsLoad){
//                value = execute(key);
//                LOG.info("uce-core-cache-CacheSupport：本地缓存["+getCacheId()+"]，key["+key+"]不存在，走数据库查询，返回结果["+value+"]");
//
//                if(enableTimeOut) {
//                    storage.set(newKey, value, timeOut);
//                } else {
//                    storage.set(newKey, value);
//                }
//            }
//            LOG.info("uce-core-cache-CacheSupport：本地缓存["+getCacheId()+"]，key["+key+"]不存在，bIsLoad=true,不走数据库查询，返回结果[null]");
//            return null;
//        } catch(ValueIsBlankException e) {
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]存在，value为空串，返回结果[null]");
//            //key存在，value为空串
//            return null;
//        } catch(ValueIsNullException ex) {
//            //key存在，value为null
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]存在，value为null，返回结果[null]");
//            return null;
//        } catch(KeyIsNotFoundException ex1) {
//            if(!bIsLoad){
//                //key不存在
//                value = execute(key);
//                LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]不存在，走数据库查询，返回结果["+value+"]");
//
//                if(enableTimeOut) {
//                    storage.set(newKey, value, timeOut);
//                } else {
//                    storage.set(newKey, value);
//                }
//            }
//            LOG.info("uce-core-cache-CacheSupport：Redis缓存["+getCacheId()+"]，key["+key+"]不存在，bIsLoad=true,不走数据库查询，返回结果[null]");
//            return null;
//        } catch(RedisConnectionException exx) {
//            //redis连接出现异常
//            value = execute(key);
//            LOG.info("uce-core-cache-CacheSupport：Redis连接出现异常，走数据库查询!");
//            return value;
//        }
//        LOG.info("uce-core-cache-CacheSupport：缓存["+getCacheId()+"]正常使用，key["+key+"]存在，返回结果["+value+"]");
//        return value;
//    }
//
//    /**
//     * 一次性取出所有内容
//     */
//    @Override
//    public Map<String, V> get() {
//        return storage.get(getContext());
//    }
//    /**
//     * 失效一组缓存
//     */
//    @Override
//    public void invalid() {
//        storage.clear(getContext());
//    }
//
//    /**
//     * 失效key对应的缓存
//     */
//    @Override
//    public void invalid(String key) {
//        storage.remove(generate(key));
//    }
//
//    /**
//     * 失效传入的多个key对应的缓存
//     */
//    @Override
//    public void invalidMulti(String... keys) {
//        String[] newKeys = new String[keys.length];
//        for(int i=0;i<keys.length;i++) {
//            newKeys[i] = generate(keys[i]);
//        }
//        storage.removeMulti(newKeys);
//    }
//
//    /**
//     * key构造时加上前缀，前缀采用{@link CacheSupport#getCacheId()}
//     */
//    @Override
//    public String generate(String key) {
//        return this.getContext().getCacheId() + KEY_SEPERATOR + doGenerate(key);
//    }
//
//    /**
//     * CacheSupport默认实现key结构构造过程方法，用户可以重写这个方法达到自定义结构的效果
//     */
//    @Override
//    public String doGenerate(String key) {
//        return key;
//    }
//
//    /**
//     * 执行回调
//     */
//    private V execute(String key) throws BussException {
//        return doGet(key);
//    }
//
//    /**
//     * 默认空实现初始化数据回调函数
//     * 当{@link CacheSupport#lazy}=false时需要重写这个方法
//     */
//    public ResultMap<String,V> doInitialization(IKeyGenerator<String> generator) throws BussException {
//        return null;
//    }
//
//    /**
//     * 执行初始化数据
//     */
//    private ResultMap<String,V> initialization() throws BussException{
//        return doInitialization(this);
//    }
//
//    public boolean getEnableTimeOut() {
//        return enableTimeOut;
//    }
//
//    public int getTimeOut() {
//        return timeOut;
//    }
//
//    /**
//     * 使用背景：数据库中数据发生变化，即doGet()执行的SQL结果有了变化；
//     * 原本存在缓存上的数据对应于某key，还没有到达刷新时间，人工刷新下该key的数据
//     * */
//    @Override
//    public boolean refresh(String key) {
//        //重新走数据库查询
//        V value = execute(key);
//        //本地缓存方式，一定是要先删除后插入的，因为有个定时任务是针对这个key的，删除key的同时也是在删除定时任务
//        //redis方式，可以不删除，直接set，过期时间也会重新计算
//        String newKey = generate(key);
//        storage.remove(newKey);
//        if(null!=value){
//            if(enableTimeOut) {
//                return storage.set(newKey, value, timeOut);
//            } else {
//                return storage.set(newKey, value);
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public long ttl(String key) {
//        return storage.ttl(generate(key));
//    }
//
//}
//
