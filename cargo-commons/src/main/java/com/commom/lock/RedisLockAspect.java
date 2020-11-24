//package com.commom.lock;
//
//import cn.hutool.extra.spring.SpringUtil;
//import com.commom.exception.BussException;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Description: 通过 Aop 实现 @RedisLock 注解功能
// * @Author: ysx
// */
//@Component
//@Aspect
//@Slf4j
//public class RedisLockAspect {
//
//    @Resource
//    private RedisLockTemplate redisLockTemplate;
//
//    private final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
//
//    private final ExpressionParser spElParser = new SpelExpressionParser();
//
//    /**
//     *  使用 @RedisLock 动态生成锁key时，根据 | 分割参与key生成的参数
//     */
//    private String lockKeySplit = "\\|";
//
//
//    @Pointcut("@annotation(com.commom.lock.RedisLock)")
//    public void redisLockPointcut() {}
//
//
//    /**
//     *  对 @RedisLock 注解功能 的实现
//     */
//    @Around("redisLockPointcut()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        //获取拦截到的方法对象
//        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        Object[] paramValues = proceedingJoinPoint.getArgs();
//        // 参与 锁标识生成的参数列表
//        Map<String,Object> joinKeyGenerateMethodParam = new HashMap<>(16);
//        // 获取 方法参数名列表
//        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
//        // 构造 spring el 执行的上下文环境
//        EvaluationContext evaluationContext = buildEvaluationContext(parameterNames, paramValues);
//
//        // 获取 @RedisLock 注解信息
//        RedisLock redisLock = method.getAnnotation(RedisLock.class);
//
//        // 动态条件判断是否需要加锁
//        String condition = redisLock.condition();
//
//        if(!"".equals(condition) && !(Boolean) getSpelValue(condition,evaluationContext)){
//            return proceedingJoinPoint.proceed();
//        }
//
//        // 判断是否为动态锁 key
//        String dynamicParam = redisLock.methodDynamicParam();
//
//
//        if(dynamicParamUsable(dynamicParam)){
//            joinKeyGenerateMethodParam.putAll(getDynamicKeyParamValue(dynamicParam,evaluationContext));
//        }else if (redisLock.methodAllParamJoinGenerate()){
//            // 开启 方法的所有参数均参与 key 的生成
//            joinKeyGenerateMethodParam.putAll(buildParam(parameterNames,paramValues));
//        }
//
//        Object target = proceedingJoinPoint.getTarget();
//        if(!redisLock.classAndMethodJoinGenerate()){
//            target = null;
//            method = null;
//        }
//        // 获取 key 生成规则器
//        String generator = redisLock.keyGenerator();
//        LockKeyGenerator lockKeyGenerator = SpringUtil.getBean(generator, LockKeyGenerator.class);
//        // lock key 生成
//        String lockKey = lockKeyGenerator.generate(target,method,redisLock.value(),joinKeyGenerateMethodParam,evaluationContext);
//        return redisLockTemplate.lockAndGetResult(lockKey, proceedingJoinPoint::proceed);
//    }
//
//
//    /**
//     *  获取本次  spel 的上下文 (其实就是将方法参数名和值映射到 EvaluationContext 中)
//     * @param parameterNames 本次参数名称
//     * @param parameterValues 本次参数值
//     * @return spel 的上下文
//     */
//    private EvaluationContext buildEvaluationContext(String[] parameterNames, Object[] parameterValues){
//        if(parameterNames.length!=parameterValues.length){
//            throw new BussException("RedisLock 参数名列表和值不匹配");
//        }
//        //设置解析 spel 所需的数据上下文
//        EvaluationContext context = new StandardEvaluationContext();
//        for(int i=0;i<parameterNames.length;i++){
//            context.setVariable(parameterNames[i],parameterValues[i]);
//        }
//       return context;
//    }
//
//    /**
//     *  将方法参数名和值映射到 Map 中
//     * @param parameterNames 本次参数名称
//     * @param parameterValues 本次参数值
//     * @return 映射后的map
//     */
//    private Map<String,Object> buildParam(String[] parameterNames, Object[] parameterValues){
//        if(parameterNames.length!=parameterValues.length){
//            throw new BussException("RedisLock 参数名列表和值不匹配");
//        }
//        HashMap<String, Object> map = new HashMap<>(16);
//        for(int i=0;i<parameterNames.length;i++){
//            map.put(parameterNames[i],parameterValues[i]);
//        }
//        return map;
//    }
//
//    /**
//     *  校验用户写的 动态参数是否符合规则
//     * @param dynamicParam 用户写的规则
//     * @return 校验结果
//     */
//    private boolean dynamicParamUsable(String dynamicParam){
//        return !dynamicParam.startsWith(lockKeySplit)&&!dynamicParam.endsWith(lockKeySplit);
//    }
//
//    /**
//     *  获取 参与 key 生成的值 如果用户添加了前缀 则 结果第0个 前缀 后面是参数值
//     *  如果用户没有添加前缀 则 结果全部为用户选中的方法参数值
//     * @param redisLockValue 注解 @RedisLock 的 value
//     * @param evaluationContext spel 的上下文
//     * @return 参与 锁key生成的值数组
//     */
//    private Map<String, Object> getDynamicKeyParamValue(String redisLockValue, EvaluationContext evaluationContext) {
//        String[] paramNames = redisLockValue.split(lockKeySplit);
//        HashMap<String, Object> map = new HashMap<>(paramNames.length);
//        // 注意 数组 第 0 个不是方法参数名 所以从1开始遍历
//        for (String paramName : paramNames) {
//            //解析表达式并获取 spel 的值
//            map.put(paramName, getSpelValue(paramName,evaluationContext));
//        }
//        return map;
//    }
//
//    /**
//     *  获取 Spel 表达式的值
//     * @param spelExpression Spel 表达式
//     * @param evaluationContext 当前 Spel 所处的上下文
//     * @return Spel 表达式的值
//     */
//    private Object getSpelValue(String spelExpression, EvaluationContext evaluationContext){
//        return spElParser.parseExpression(spelExpression).getValue(evaluationContext);
//    }
//
//
//}