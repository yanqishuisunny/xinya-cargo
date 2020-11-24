package com.commom.lock;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Lock key 生成策略
 * @author ysx
 */
@Component("simpleLockKeyGenerator")
public class SimpleLockKeyGenerator implements LockKeyGenerator {
    /**
     *  分布式锁key 生成策略接口
     * @param targetClass @RedisLock 注解所在类对象 当 classAndMethodJoinGenerate 为 false 参数为 null
     * @param method @RedisLock 注解所在方法对象 当 classAndMethodJoinGenerate 为 false 参数为 null
     * @param key @RedisLock 注解的 value
     * @param slElMap @RedisLock 注解 methodDynamicParam 键为 | 分隔的每个Spel表达式，值为对应spel表达式的值
     * @param evaluationContext Spel 所需的上下文环境
     * @return 通过策略生成的分布式锁 key
     */
    @Override
    public String generate(Object targetClass, Method method, String key, Map<String, Object> slElMap, EvaluationContext evaluationContext) {

        HashMap<String, Object> map = new HashMap<>(16);
        if(targetClass!=null){
            Class<?> targetClassClass = targetClass.getClass();
            map.put("class",targetClassClass.toGenericString());
            // 包名称
            map.put("package",targetClassClass.getPackage());
        }

        // 方法名称
        if(method!=null){
            map.put("methodName",method.getName());
        }

        // 动态参数列表
        if(slElMap!=null&&!slElMap.isEmpty()){
            map.put("methodDynamicParam",slElMap);
        }
        // 转为JSON字符串
        String jsonStr = JSONUtil.toJsonStr(map);
        // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
        return key+ SecureUtil.sha256(jsonStr);
    }
}
