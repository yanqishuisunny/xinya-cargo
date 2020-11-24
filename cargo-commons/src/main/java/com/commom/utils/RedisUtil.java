package com.commom.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 李雷
 * @Date : 2018/6/8
 * @Description : 基于 spring和redis的redisTemplate工具类 针对所有的hash 都是以h开头的方法 针对所有的Set
 * 都是以s开头的方法 不含通用方法 针对所有的List 都是以l开头的方法
 */
public class RedisUtil {

    public static final Long EXPIRE_TIME = 60 * 60 * 4L;
    private static final Long SUCCESS = 1L;
    private static long timeout = 9999; //获取锁的超时时间

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    public static long DAY = 86400L;

    public static RedisTemplate<String, Object> redisTemplate;
    public static RedisTemplate<String, Object> redisObjectTemplate;

    /**
     * @param key  键
     * @param time 时间(秒)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 指定缓存失效时间
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    /**
     * @param key  键
     * @param time 时间(秒)
     * @return
     * @author Carlos
     * @Date : 2020/6/8
     * @Description : 作废登录时效
     */
    public static boolean toVoid(String key, long time) {
        try {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 根据key 获取过期时间
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }



    /**
     * 获取数据Map
     *
     * @param mapName
     * @return
     */
    public static Map<String, Object> getMapValue(String mapName) {
        HashOperations<String, String, Object> hps = redisTemplate.opsForHash();
        return hps.entries(mapName);
    }


    /**
     * @param key 键
     * @return true 存在 false不存在
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 判断key是否存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 可以传一个值 或多个
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 删除缓存
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================

    /**
     * @param key 键
     * @return 值
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 普通缓存获取
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * key-value取值
     * 获取对象
     *
     * @param key
     * @return
     */
    public static  <T> T getObject(final String key) {
        return redisObjectTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keybytes = redisObjectTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keybytes)) {
                    byte[] valuebytes = connection.get(keybytes);

                    long exp = connection.ttl(keybytes);
                    if (exp > 0) {
                        @SuppressWarnings("unchecked")
                        T value = (T) SerializeUtil.unserialize(valuebytes);
                        return value;
                    }
                }
                return null;
            }
        });
    }

    /**
     * 保存对象数据
     *
     * @param key      key
     * @param value    对象数据
     * @param expireat 有效期  单位?
     */
    public static void putObject(final String key, Object value, final Long expireat) {
        final byte[] vbytes = SerializeUtil.serialize(value);
        redisObjectTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] fkey = redisObjectTemplate.getStringSerializer().serialize(key);
                if (connection.exists(fkey)) {
                    connection.del(fkey);
                }
                connection.set(fkey, vbytes);
                if (null != expireat && expireat > 0) {
                    connection.expire(fkey, expireat);
                }
                return null;
            }
        });
    }


    /**
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description :普通缓存放入
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 普通缓存放入并设置时间
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 递减
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : HashGet
     */
    public static Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @param key 键
     * @return 对应的多个键值
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 获取hashKey对应的所有键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : HashSet
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : HashSet 并设置时间
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 向一张hash表中放入数据,如果不存在将创建
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 向一张hash表中放入数据,如果不存在将创建
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 删除hash表中的值
     */
    public static void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 判断hash表中是否有该项的值
     */
    public static boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : hash递增 如果不存在,就会创建一个 并把新增后的值返回
     */
    public static double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : hash递减
     */
    public static double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * @param key 键
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 根据key获取Set中的所有值
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 根据value从一个set中查询,是否存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将数据放入set缓存
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将set数据放入缓存
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key 键
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 获取set缓存的长度
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 移除值为value的
     */
    public static long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // ===============================list=================================

    /**
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 获取list缓存的内容
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key 键
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 获取list缓存的长度
     */
    public static long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 通过索引 获取list中的值
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将list放入缓存
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将list放入缓存
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将list放入缓存
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 将list放入缓存
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 根据索引修改list中的某条数据
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 移除N个值为value
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 尝试获取分布式锁 暂不支持
     */
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        /*
         * redisTemplate.opsForValue().set("",""); String result =
         * redisTemplate.execute((RedisCallback<Boolean>) redisConnection->{ Jedis jedis
         * = (Jedis)redisConnection.getNativeConnection(); }); if
         * (LOCK_SUCCESS.equals(result)) { return true; }
         */
        return false;
    }

    /**
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     * @author 李雷
     * @Date : 2018/6/8
     * @Description : 释放分布式锁 暂不支持
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        /*
         * //redisTemplate. String script =
         * "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end"
         * ; Object result = jedis.eval(script, Collections.singletonList(lockKey),
         * Collections.singletonList(requestId));
         *
         * if (RELEASE_SUCCESS.equals(result)) { return true; }
         */
        return false;

    }

    /**
     * 加锁，无阻塞
     *
     * @param
     * @param
     * @return
     */
    public static synchronized Boolean tryLock(String key, String value, long expireTime) {


        Long start = System.currentTimeMillis();
        try{
            for(;;){
                //SET命令返回OK ，则证明获取锁成功
                Boolean ret = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
                if(ret){
                    return true;
                }
                //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
                long end = System.currentTimeMillis() - start;
                if (end>=timeout) {
                    return false;
                }
            }
        }finally {

        }

    }


    /**
     * 解锁
     *
     * @param
     * @param
     * @return
     */
    public static synchronized Boolean unlock(String key) {
        return redisTemplate.delete(key);
    }

}
