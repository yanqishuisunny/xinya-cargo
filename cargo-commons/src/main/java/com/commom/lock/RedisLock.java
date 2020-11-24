//package com.commom.lock;
//
//import java.lang.annotation.*;
//
///**
// * @Description:
// * @RedisLock 生成key的组成包括以下(其实就是注解的各个属性):
// * ---------------------
// * 一般使用的参数:
// *
// * 默认value: 写死固定的 key 值元素 , 实际redis锁 key 的生成策略会以该值做前缀 在使用中建议 自行根据业务定义，默认 LOCK_
// *
// * classAndMethodJoinGenerate：类及方法签名是否参与动态key的生成，默认true
// *
// * methodDynamicParam:哪些方法参数参与key的生成(格式 #参数名|#参数名.属性值 支持多个spEl表达式,每个spEl表达式之间用|分隔)
// *
// * ---------------------
// * 其他可选参数:
// * methodAllParamJoinGenerate:方法的所有参数均参与 key 的生成,和 methodDynamicParam 互斥，若配置了 methodDynamicParam，则该参数不生效
// *
// * keyGenerator:key的生成规则类，值为 spring 容器中的 beanName，因此用户可以自定义key的生成规则，实现 LockKeyGenerator 接口即可
// *
// * ----------------------
// * @RedisLock 注解其他不参与 key 生成的属性
// * `
// * condition： 使用 spel 表达式判断是否加锁(例如只有传入的手机号是138xxx才加锁，否则不加锁这类场景)
// * `
// * @author: ysx
// */
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD})
//@Documented
//public @interface RedisLock {
//  /**
//   *  写死固定的 key 值元素 默认的 实际redis锁 key 的生成策略会以该值做前缀
//   *  在使用中建议 自行根据业务定义
//   */
//  String value() default "LOCK_";
//
//
//  /**
//   * 动态获取方法参数值 格式 #参数名|#参数名.属性值 支持多个spEl表达式,每个spEl表达式之间用 | 分隔
//   */
//  String methodDynamicParam() default "";
//
//  /**
//   *  类及方法是否参与动态key的生成
//   */
//  boolean classAndMethodJoinGenerate() default true;
//
//  /**
//   * 方法的所有参数均参与 key 的生成 与 methodDynamicParam 互斥
//   */
//  boolean methodAllParamJoinGenerate() default false;
//
//  /**
//   * 默认 生成规则 值为 spring 容器中的 beanName
//   */
//  String keyGenerator() default "simpleLockKeyGenerator";
//
//  /**
//   *  使用 spel 表达式判断是否加锁
//   */
//  String condition() default "";
//
//
//
//}
