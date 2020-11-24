package com.commom.lock;


import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description: 分布式锁key 生成策略接口
 * @author ysx
 */
@FunctionalInterface
public interface LockKeyGenerator {

    /**
     *  分布式锁key 生成策略接口
     * @param targetClass @RedisLock 注解所在类对象 当 classAndMethodJoinGenerate 为 false 参数为 null
     * @param method @RedisLock 注解所在方法对象 当 classAndMethodJoinGenerate 为 false 参数为 null
     * @param key @RedisLock 注解的 value
     * @param slElMap @RedisLock 注解 methodDynamicParam 键为 | 分隔的每个Spel表达式，值为 根据Spel 得到的值
     * @param evaluationContext Spel 所需的上下文环境
     * @return 通过策略生成的分布式锁 key
     */
    String generate(Object targetClass, Method method, String key, Map<String, Object> slElMap, EvaluationContext evaluationContext);
}
