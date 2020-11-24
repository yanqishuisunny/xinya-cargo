package com.commom.lock;

import com.commom.exception.BussException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * 使用 redisson 实现分布式锁的模板类服务
 * @author ysx
 */
@Component
@Slf4j
public class RedisLockTemplate {

	@Resource
	private RedissonClient redissonClient;

	/**
	 * 使用分布式锁处理带有返回值业务
	 * @param lockKey 锁的key
	 * @param serviceProcess 具体需要处理的业务方法
	 * @return 需要加锁业务产生的业务结果
	 */
	<T> T lockAndGetResult(String lockKey, ResultBusinessInterface<T> serviceProcess) throws Throwable{
		RLock lock = redissonClient.getLock(lockKey);
		try {
			boolean lockResult = lock.tryLock(60, -1, TimeUnit.SECONDS);
			if(!lockResult){
				throw new BussException("获取分布式锁超时");
			}
			return serviceProcess.get();
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * 使用分布式锁处理不带有返回值业务
	 * @param lockKey 锁的key
	 * @param serviceProcess 具体需要处理的业务方法 注意这里将 Runnable 当普通函数接口
	 */
	public void lock(String lockKey,Runnable serviceProcess){
		RLock lock = redissonClient.getLock(lockKey);
		try {
			boolean lockResult = lock.tryLock(60, -1, TimeUnit.SECONDS);
			if(!lockResult){
				throw new BussException("获取分布式锁超时");
			}
			serviceProcess.run();
		}catch (Exception e){
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
}
