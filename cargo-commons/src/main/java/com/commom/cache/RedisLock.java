package com.commom.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁
 * 为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
 * 互斥性。在任意时刻，只有一个客户端能持有锁。
 * 不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * 具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * 解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 */

@Component
@Slf4j
public class RedisLock {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 释放锁脚本，原子操作
     */
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    /**
     * 获取分布式锁，原子操作
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @param expire
     * @param timeUnit
     * @return 是否获取成功
     */
    public Boolean tryLock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
        try{
            RedisCallback<Boolean> callback = (connection) ->
                    connection.set(
                            lockKey.getBytes(Charset.forName("UTF-8")),
                            requestId.getBytes(Charset.forName("UTF-8")),
                            Expiration.seconds(timeUnit.toSeconds(expire)),
                            RedisStringCommands.SetOption.SET_IF_ABSENT);
            return (Boolean)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("redis lock error.", e);
        }
        return false;
    }

    /**
     * 释放锁 是否释放成功
     * @param lockKey
     * @param requestId 唯一ID
     * @return
     */
    public boolean releaseLock(String lockKey, String requestId) {
        RedisCallback<Boolean> callback = (connection) ->
                connection.eval(UNLOCK_LUA.getBytes(),
                        ReturnType.BOOLEAN ,1,
                        lockKey.getBytes(Charset.forName("UTF-8")),
                        requestId.getBytes(Charset.forName("UTF-8")));
        return (Boolean)redisTemplate.execute(callback);
    }

    /**
     * 获取Redis锁的value值
     * @param lockKey
     * @return
     */
    public String get(String lockKey) {
        try {
            RedisCallback<String> callback = (connection) ->
                    new String(connection.get(lockKey.getBytes()), Charset.forName("UTF-8"));
            return (String)redisTemplate.execute(callback);
        } catch (Exception e) {
            log.error("get redis occurred an exception", e);
        }
        return null;
    }
}
