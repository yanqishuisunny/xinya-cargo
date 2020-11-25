# xinya-cargo

网络货运平台# xinya-cargo

# 目录

* [简介](#简介)
* [已封装功能](#已封装功能)
* [启动并测试功能](#启动并测试功能)
* [需要介绍的基础功能](#需要介绍的基础功能)
  * [统一响应及错误处理](#统一响应及错误处理)
  * [jwt认证使用](#jwt认证使用)
  * [接口签名使用](#接口签名使用)
* [框架功能扩展](#框架功能扩展)
  * [扩展spring静态注入功能](#扩展spring静态注入功能)
    * [诞生背景](#诞生背景)
    * [使用说明](#使用说明)
    * [实现原理](#实现原理)
  * [扩展springmvc接收json字段独立与接口参数绑定功能](#扩展springmvc接收json字段独立与接口参数绑定功能)
    * [诞生背景](#诞生背景-2)
    * [使用说明](#使用说明-2)
    * [实现原理](#实现原理-2)
  * [扩展springmvc对响应json字段属性忽略功能](#扩展springmvc对响应json字段属性忽略功能)
    * [诞生背景](#诞生背景-3)
    * [使用说明](#使用说明-3)
    * [实现原理](#实现原理-3)
* [常用工具类实现](#常用工具类实现)
  * [分布式锁注解实现](#分布式锁注解实现)
  * [复杂场景下map与bean互转工具实现](#复杂场景下map与bean互转工具实现)
* [独立组件开发](#独立组件开发)
  * [RabbitMq可靠投递组件](#rabbitmq可靠投递组件)



# 简介
这个项目是我抽象出来的一个种子项目(脚手架)，便于快速进行基于 springboot 前后分离的后端项目开发，有 springboot 项目基础的可直接下载使用，已有项目，只想使用本项目的框架扩展功能或工具类等，阅读相关说明后也可单独复制使用，主要须知依赖包使用的版本如下:

依赖包 | 版本 | 作用
---|---|---
springboot | 2.1.6.RELEASE | springboot基础
mybatis-plus | 3.1.0 | 简化基础crud编写
lombok | 1.18.12 | 简化实体模板代码
swagger2 | 2.0.3 | 基于swagger2的在线接口文档
springcloud openfeign |   | 实现服务之间的掉用
springcloud nacos | 2.1.1 | 实现服务注册
shiro | 1.4.0 | 实现登录统一认证拦截
rabbitmq | 2.1.6.RELEASE | 消息

由上面须知jar包可以看出项目的主体主架所使用的技术，因此我不会多赘述上面那些jar包如何使用，这里假设你已经知道像 mybatis-plus，pagehelper，knife4j等的使用，如从未接触过，建议学习相关组件后在使用本项目，本项目在分包上很符合大众规范，因此项目基础功能基本看看就会(基础功能还需要额外学习成本肯定是不合适的，简单使用，看下下面[需要介绍的基础功能](#需要介绍的基础功能)即可，要是老司机不看也可无师自通，但是建议看下)，一些我自己封装的可选功能需要看下我下面写的[框架功能扩展](#框架功能扩展)   

# 已封装功能
常规功能上这个基础项目实现了常用的   
1. [全局统一格式响应，错误处理](#统一响应及错误处理)
2. [jwt认证](#jwt认证使用)
3. [接口签名](#接口签名使用)
4. 请求日志,springcache的配置,Knife4j文档配置等的集成封装(不在详细介绍)  
5. 基础代码生成器集成(MybatisPlusGeneratorCode)(不在详细介绍)

扩展功能上，本项目实现了3个基于spring框架，springmvc框架的扩展功能，分别是  
1. [扩展spring静态注入功能](#扩展spring静态注入功能)
2. [扩展springmvc接收json字段独立与接口参数绑定功能](#扩展springmvc接收json字段独立与接口参数绑定功能)
3. [扩展springmvc对响应json字段属性忽略功能](#扩展springmvc对响应json字段属性忽略功能)

常用工具上封装了2个常用但甚少有人封装的工具  
1. [分布式锁注解实现](#分布式锁注解实现)
2. [复杂场景下map与bean互转工具实现](#复杂场景下map与bean互转工具实现)  


# 启动并测试功能
为了方便测试基础功能项目已经写好了一套测试接口，用于使用者自行测试相关功能，看下controller即知

项目启动前请先初始化数据库(resources/sql目录下)，修改resources/config/application-dev.yml 中 数据库和redis连接即可直接启动
启动完毕后，可访问如下地址查看接口文档  
http://localhost:8093/doc.html  
测试时可以结合下面的[基础功能介绍测试](#需要介绍的基础功能)  
测试完毕，希望使用该脚手架作为项目基础，相信各位使用过springboot项目大佬的应该都知道如何删除不需要的功能，及上面的测试代码，这里就不在赘述了  

ps：下面功能测试代码，仅是为了方便演示测试功能，并不代表实际开发中会采用这种逻辑或规范  
# 需要介绍的基础功能
## 统一响应及错误处理
关于统一响应及异常处理相信各位老司机都知道怎么做，我这里采用的是比较常用的做法，利用 `springmvc` 的 `ResponseBodyAdvice` 和 `@RestControllerAdvice` 来做,这样代码编写比较方便，优雅   
本项目的一般统一响应结果格式为:
```json
{
  "code": 200,
  "message": "成功!",
  "data": {},
  "currentTime": 1588990989985
}
```
data为本次响应的结果数据,当响应结果为基本类型 即 布尔，字符串，数字，日期时，后端保证包装为对象，示例如下:
```json
{
  "code": 200,
  "message": "成功!",
  "data": {
    "result": false
  },
  "currentTime": 1588990989985
}
```
但是对于基本类型的集合形式，不做包装，示例如下:
```json
{
  "code": 200,
  "message": "成功!",
  "data": [
    true,
    false
  ],
  "currentTime": 1588991371841
}
```

下面看一个测试接口的编写(UserBaseActionController#login):
在在线调试文档可输入入参  
post application/json
```json
{
"phone":"13832249872",
"password":"123456"
}
```
```java
    @ApiOperation(value = "用户手机号密码登录",notes = "用户手机号密码登录")
    @PostMapping("/login")
    public LoginVo login(@RequestBodyParam String phone, @RequestBodyParam String password)  {
        if(StrUtil.hasBlank(phone,password)){
            throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_PARAM_CHECK);
        }
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("phone_num", phone));
        if(userInfo == null){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_NOT_EXIST);
        }
        if(!Objects.equals(password,userInfo.getPassword())){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_LOGIN);
        }
        return getLoginVo(userInfo);
    }
```
响应结果由 `ResponseGlobalAdvice` 统一封装，开发在Controller中返回数据原始类型即可，无需手动包装 

对于异常处理，这里对于所有不符合业务正常逻辑的全部抛出业务异常，响应给客户端对应的异常状态码，异常的统一处理在`GlobalExceptionHandler`  统一处理，开发在编写代码过程中，只管抛就行  
## jwt认证使用
jwt技术这里就不做介绍了，项目已经将jwt相关封装完毕，jwt相关认证代码在
`com.ysx.common.auth` 包下,需要配置的属性也在配置文件中列出
```ymal
ysx:
  # 认证模块
  auth:
    # jwt 加密秘钥
    secretKey: dsdkljisisbggbfdsaea
    # jwt token 过期时间 30 天 单位 毫秒
    ttlMillis: 2592000000
    # 客户端发送请求 token 所放的请求头名称
    tokenHeader: token
```
1. 整个流程就是未登陆用户访问登陆接口(UserBaseActionController#login),接口响应 jwt生成的token给客户端  
ps：登陆接口是仅供参考的代码，开发时根据自己业务参考编写即可
2. 客户端在访问后端需要登陆认证的接口时，携带请求头 为 token(配置文件可配) 的值即可完成认证

下面看一个需要登陆认证的测试接口的编写(UserBaseActionController#getSelfInfo):
```java
    @ApiOperation(value = "查询个人信息",notes = "查询个人信息")
    @GetMapping("/getSelfInfo")
    public UserInfoVo getSelfInfo(TokenUserModel userModel){
        Long userId = userModel.getLoginId();
        UserInfo userInfo = userInfoService.getById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        AccountCoin accountCoin = accountCoinService.getOne(new QueryWrapper<AccountCoin>().eq("user_id", userId));
        userInfoVo.setCoinCount(accountCoin.getCoinUsable());
        return userInfoVo;
    }
```
对于需要登陆的接口，只需要在方法参数列表中加上 TokenUserModel 对象即可，未登录访问该接口会报错  
对于不需要登陆的接口正常写参数列表即可  
对于登陆和不登陆均能访问的接口，在TokenUserModel参数前 加 @Anonymous 注解即可  
例:（UserBaseActionController#testAnonymous）
```java
    @ApiOperation(value = "登陆不登陆均可访问",notes = "登陆不登陆均可访问")
    @GetMapping("/testAnonymous")
    public String testAnonymous(@Anonymous TokenUserModel userModel){
        if(userModel==null){
            return "未登录";
        }
        return "已登录，id为:"+userModel.getLoginId();
    }
```
## 接口签名使用
接口签名是需要客户端服务端相互配合的，先说下本项目的接口签名规则，基本适用于任何形式的http接口  
部分规则，及代码参考了搜索到的一篇博客，一时找不到了，找到后会加个参考链接
```
根据规则生成签名
规则如下:
总体: sha256(请求头字符串+body体json字符串+普通键值对参数字符串+restful 类型连接上参数字符串)

 *   请求头必须携带
 *   platform (不同端可选值 android  ios  pc  web third)
 *   version (当前默认传 1.0)
 *   timestamp (时间戳毫秒值)
 *   signature (客户端签名)


 请求头字符串生成规则:
  必传的 4 个 请求头,signature 不参与生成规则，其他 3 个请求头 首先按照 请求头名称自然排序，之后按照 名称=值# 的方式生成字符串
  生成的字符串参考结果: platform=web#timestamp=1586421539000#version=1.0#

 body 字符串生成规则
 建议将 body 的 json 串 压缩后传递，尽量不要传递漂亮格式(包含大量换行空格）,这样一方面加密不易出错，另一方面节省流量
 压缩后 加上 #  号
 生成的字符串参考结果: {"name":"zhangsan","age":22}#

 普通的键值对参数字符串生成规则
 首先按照参数名称自然排序
 对于 普通的键值对参数 规则为 param=value#
 对于 checkbox 键值对参数 规则为 param=value1,value2#  对于值 value1,value2 按照自然排序
 生成的字符串参考结果: param3=3,33#param4=4#

 restful 类型 连接上的参数
 连接上的参数值 按照自然排序 用 , 号隔开，末尾不需要加 # 号
 生成的字符串参考结果: 1,2

 按照参考结果生成的完整字符串为:
 platform=web#timestamp=1586421539000#version=1.0#{"name":"zhangsan","age":22}#param3=3,33#param4=4#1,2

 执行
 sha256(platform=web#timestamp=1586421539000#version=1.0#{"name":"zhangsan","age":22}#param3=3,33#param4=4#1,2)
 加密后生成的 signature 转为全小写
 9b51665b4ea30173334d8087efcb04a826c21ea588f98804f78809497a822de1

 将生成的 signature 添加到 header 中
```
这个后端使用也很简单，配置需要拦截验签的接口即可(com.ysx.common.sign.SignConfig)  
在配置的接口范围内，可使用注解 @SignIgnore 忽略该接口的验签  
在配置的接口范围内，可使用注解 @SimpleSign 忽略上面参数验签的复杂规则，只使用请求头验签

# 框架功能扩展
在使用框架的过程中，一些功能可能并不方便使用，或者框架未提供相关功能，需要在框架现有基础上做一些功能扩展，以下3个框架的功能扩展均为我本人在工作中为开发更为便携快速而实现(不一定适合所有公司或所有场景，请根据实际情况选择使用)
以下实现的功能我一般遵循2个设计原则  
1. 使用起来尽量简单，且对其他已有框架功能无影响   
2. 实现起来尽量简单，越简单越好

因此下面的扩展功能，我都是采用自定义注解实现，使用时，只需使用对应功能的自定义注解即可，和框架原生的注解使用一样便利  

下面我的实现方案也是我目前所知的能达到效果的最简方案，若有更简单的方案，欢迎讨论 `^V^` 
## 扩展spring静态注入功能
### 诞生背景
在开发过程中，我们会封装各种各样的工具类，方法一般都是静态方法，这样我们使用会非常的方便，但是如果我们的工具类需要spring框架的一些功能，会发现做成静态方法比较麻烦，纠其原因是spring并不支持直接通过注解静态注入，当然有各种spring原生提供的方案去曲折实现静态注入，但是确实比较麻烦，基于这样的情况下开发了两个自定义注解去支持静态注入，首先明确一点，spring并不建议直接的静态注入，不然spring不会没有这个功能的，因此使用的选择权在各位老司机，大家根据自己的需要权衡选择使用即可
### 使用说明
> 自定义的注解为 `@StaticAutowired` 和 `@StaticValue`  

使用很简单，和@Autowired,@Value，使用基本一致，但是 @StaticAutowired,@StaticValue只能作用于字段，不能作用于方法  
例:(UserBaseActionController#testStaticInjection)
```java
@RestController
@RequestMapping(BASE_APP_URL +"/user/action")
public class UserBaseActionController {
    @StaticAutowired
    private static AccountCoinService accountCoinService;
    @StaticValue("${server.port}")
    private static  Integer currentPort;

    @ApiOperation(value = "测试静态注入",notes = "测试静态注入")
    @GetMapping("/testStaticInjection")
    public String testStaticInjection(){
        return accountCoinService.toString()+currentPort;
    }
}
```

## 扩展springmvc接收json字段独立与接口参数绑定功能
### 诞生背景
我们前后端交互一般都是使用 http 协议，在我们实际开发中一般提交数据会使用 post 请求，但是post请求一般有2种方式，一种是表单的形式，以键值对提交，另一种则是 json 的形式提交，这在事实上造成了不统一，只能靠文档来约束，如果规定只允许表单形式提交，那么一些复杂的嵌套数据可能很难提交，所以一般我们可能会规定统一采用json串的形式传递，这种方式各位老司机都熟，在后端参数接收上只需要加一个`@RequestBody` 即可,但是有个问题，就是这种方式我必须以对象的形式去接收参数，也就是说，如果我约定了这种规范，post请求客户端只传递一个参数，那么我们还需要为这一个参数去创建一个类，否则就是复用其他已有的类属性，这两种方案我觉得都不好，因此我希望对应 json形式的post传参，我也可以像get请求那样，直接在controller 方法参数列表中取指定的属性，类似 `@RequestParam`,这就是自定义注解 `@RequestBodyParam` 的诞生背景，`@RequestBodyParam`相当于 @RequestBody的上位替代，`@RequestBodyParam`也可以像 `@RequestBody` 直接对一个复杂对象赋值，只是目前 `@RequestBodyParam` 还没有结合`@Valid`相关的校验框架，目前我是简单属性直接用 `@RequestBodyParam`，复杂对象属性继续使用 `@RequestBody`结合`@Valid`使用。 

后面有空我会将`@RequestBodyParam` 结合`@Valid`使用  

ps：我对 `@RequestBodyParam` 做了兼容，基本无论post以何种传参方式，后端均可接收到参数
### 使用说明
> 注解为 @RequestBodyParam

UserBaseActionController#login
```java
    @ApiOperation(value = "用户手机号密码登录",notes = "用户手机号密码登录")
    @PostMapping("/login")
    public LoginVo login(@RequestBodyParam String phone, @RequestBodyParam  String password)  {
        if(StrUtil.hasBlank(phone,password)){
            throw new BusinessException(ResponseCodeEnum.ERROR_SYSTEM_PARAM_CHECK);
        }
        UserInfo userInfo = userInfoService.getOne(new QueryWrapper<UserInfo>().eq("phone_num", phone));
        if(userInfo == null){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_NOT_EXIST);
        }
        if(!Objects.equals(password,userInfo.getPassword())){
            throw new BusinessException(ResponseCodeEnum.ERROR_USER_LOGIN);
        }
        return getLoginVo(userInfo);
    }
```
客户端传参
post application/json
```json
{
"phone":"13832249872",
"password":"123456"
}
```
### 实现原理
抽空写，各位老司机也可以研究下实现代码，注释很详细，很容易看出原理了

## 扩展springmvc对响应json字段属性忽略功能
以下内容全部基于使用 jackson 作为 json框架，如使用其他 json 框架，如 fastjson，可以不必阅读本段
### 诞生背景
我们接口在响应数据时会响应一个json串，这是当前前后端数据交互的主流方式，我们的json串基本都是响应的对象转化而来，但是有一种场景，例如 A 接口需要响应包含密码的用户信息，B接口需要响应不包含密码的用户信息，其他信息相同，我们都知道如果要忽略对象的字段转为json，响应给客户端，那么可以在字段上添加注解 `@JsonIgnore`，忽略该字段的转化，但是注解是作用于字段，这样所有接口，只要响应该对象，都会忽略该字段，并不符合我们的需求，因此我们需要一个更为灵活的，粒度为接口级别的方式忽略，使用起来要简单，并且不能对其他框架已有功能或注解造成影响，由此作用于接口方法的注解 `@JsonFieldIgnore` 诞生  

ps:当然还有其他迂回方案，，例如使用`JsonView`，但是使用起来非常的麻烦 或者每个接口只要字段不一样，那就新建一个类，这种也很规范，但是真的会创建海量的类，而且会有大量的重复字段，我们并没有采用这种规范的方案(所以完全看项目选择)

ps: 需要的效果是响应给客户端的 json 字符串完全没有需要要忽略的字段，而不是将字段值赋值为空，因为有些场景字段存在本身就会暴露一些设计(例如虚假xx的字段)，而且不必要的json字段不响应给客户端也节省带宽流量不是
### 使用说明
> 注解为 @JsonFieldIgnore

直接在接口上添加注解，并指定需要忽略的字段即可  
例:登陆用户查询个人信息，可以查询到密码信息,过滤了不需要给客户端的 "isDeleted","updateTime" 两个字段  
(UserBaseActionController#getSelfInfo)  
ps：在强调一遍，这是演示代码，并不代表生产项目代码，不要问我为啥可以查密码，为啥没有什么加密措施
```java
    @ApiOperation(value = "查询个人信息",notes = "查询个人信息")
    @GetMapping("/getSelfInfo")
    @JsonFieldIgnore({"isDeleted","updateTime"})
    public UserInfoVo getSelfInfo( TokenUserModel userModel){
        //...代码略
        return userInfoVo;
    }
```
同样的响应对象，我需要不返回密码信息和"isDeleted","updateTime"字段  
UserBaseActionController#getUserInfo
```java
    @ApiOperation(value = "查询用户信息",notes = "查询用户信息")
    @GetMapping("/{userId}")
    @JsonFieldIgnore({"password","isDeleted","updateTime"})
    public UserInfoVo get(@PathVariable("userId")Long userId){
        //...代码略
        return userInfoVo;
    }
```

### 实现原理
抽空写，各位老司机也可以研究下实现代码，注释很详细，很容易看出原理了
# 常用工具类实现

## 分布式锁注解实现
这个工具类就是为了简化加分布式锁的相关代码，分布式锁的实现本身由 redis 实现，本工具只是在上层做了一些使用的封装，使用起来很简单，在需要加锁的方法上加上 `@RedisLock` 即可,这个工具类还可以进一步完善，例如独立作为一个分布式锁的独立组件，只需配置，支持各种方式(redis，zk等)的分布式锁，这些功能待实现，便于使用  

下面介绍下该注解的使用，加锁我们都知道一定要有个锁的标志，这个标志我们这里用字符串表示，所以加锁的过程可以说是这个标志生成的过程，生成后使用 redis 存储该标志以达到加锁的目的，默认标志的生成包含3部分，固定值+类方法签名+方法参数值，具体如下:  
```
@RedisLock 生成key的组成包括以下(其实就是注解的各个属性):  
---------------------
一般使用的参数:

默认value: 写死固定的 key 值元素 , 实际redis锁 key 的生成策略会以该值做前缀 在使用中建议 自行根据业务定义，默认 LOCK_

classAndMethodJoinGenerate：类及方法签名是否参与动态key的生成，默认true

methodDynamicParam:哪些方法参数参与key的生成(格式 #参数名|#参数名.属性值 支持多个spEl表达式,每个spEl表达式之间用|分隔)

---------------------
其他可选参数:
methodAllParamJoinGenerate:方法的所有参数均参与 key 的生成,和 methodDynamicParam 互斥，若配置了 methodDynamicParam，则该参数不生效

keyGenerator:key的生成规则类，值为 spring 容器中的 beanName，因此用户可以自定义key的生成规则，实现 LockKeyGenerator 接口即可
```
@RedisLock 注解其他不参与 key 生成的属性
```
condition： 使用 spel 表达式判断是否加锁(例如只有传入的手机号是138xxx才加锁，否则不加锁这类场景)
```

因此如果在方法上直接添加 `@RedisLock` 注解,不带任何注解内的属性，由上面注解属性可知 classAndMethodJoinGenerate属性默认为true，那么加了注解的方法和 方法用 `synchronized` 修饰没有任何区别，只是一个是面向单机的，一个面向集群的,这种忽略方法参数，直接锁整个方法建议谨慎使用，最好根据方法参数加锁，降低锁的范围

在举一个示例:(UserBaseActionController#registered)
```java
    @ApiOperation(value = "用户手机号密码注册",notes = "用户手机号密码注册")
    @PostMapping("/registered")
    @RedisLock(value = LockKeyPrefixConstant.USER_BASE_ACTION_REGISTER,methodDynamicParam = "#registerInput.phoneNum")
    public LoginVo registered(@RequestBody @Valid RegisterInput registerInput)  {
        //...代码略
        return getLoginVo(userInfo);
    }
```
这个示例就是 参与 锁 key 生成的有 key前缀 和方法参数 phoneNum，即当同一个手机号同一时间注册，只有一个可以获得锁，但是不同手机号是没有锁竞争关系的

## 复杂场景下map与bean互转工具实现
简单的 map 与 bean 相互转换是很容易的，但是一些复杂场景可能比较麻烦，而bean 和 map 之间相互转换在开发中是比较常见的场景，由此诞生了这个工具类  
先说注意: 注意:两个bean之间循环引用不可使用本工具  
功能如下:  
1. 支持转化多层继承的类对象，可指定转化到的继承层级  
2. 常用增强功能(日期转换，将对象属性抓取到map，忽略转换,自定义属性映射)，使用注解实现，使用方便  
3. 支持常用类型-枚举的转化(map-》Object是根据现有map属性推断应该的枚举)  
4. 支持用户自定义map键值忽略策略，只需实现相关扩展接口即可   

增强功能全部基于注解实现，所有注解全部基于字段级别
```
@MapKeyMapping注解
对字段要映射的key起一个别名，map的key默认使用字段名，加上该注解后，优先使用注解上加的别名

@IgnoreMapMapping注解
忽略该字段转为map的键值

@CatchSingleProperty注解
有的对象属性是另外一个javaBean(如学生对象的一个成员变量是学校对象)或者枚举，此时我并不希望map中保存整个对象，而是该对象的某个属性
可使用这个注解

@CatchAllProperty注解
有的对象属性是另外一个javaBean(如学生对象的一个成员变量是学校对象)或者枚举，此时我并不希望map中保存整个对象，而是该对象的所有属性
ps:在该对象所属类中仍然可以使用上面所列注解,即是一个递归调用过程

@DateMapping
日期和字符串转换时使用
```


# 独立组件开发
## RabbitMq可靠投递组件
一个springboot 的 starter 直接引入即可  
这个没整理好呢，最近没有时间，先挂个目录，有空整理好了放

