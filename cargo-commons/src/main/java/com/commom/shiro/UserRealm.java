package com.commom.shiro;

import com.commom.cache.CachePre;
import com.commom.core.BusCode;
import com.commom.exception.BussException;
import com.commom.utils.RedisUtil;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 *
 * Spring会实例化FactoryBean，以确定其创建的bean的类型。
 *  *
 * 由于ShiroFilterFactoryBean实现了FactoryBean接口，所以它会提前被初始化。又因为SecurityManager依赖Realm实现类、Realm实现类又依赖userLoginServiceImp，所以引发所有相关的bean提前初始化。
 *
 * ShiroFilterFactoryBean -> SecurityManager -> Realm实现类 -> userLoginServiceImp
 *
 * 但是此时还只是ApplicationContext中registerBeanPostProcessors注册BeanPostProcessor处理器的阶段，此时AnnotationAwareAspectJAutoProxyCreator还没有注册到BeanFactory中，故无法对userLoginServiceImp进行事务处理和aop编程等操作。
 *
 * @author hlh
 */

@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (!Strings.isNullOrEmpty(token) && RedisUtil.hasKey(token)) {

        }
        return simpleAuthorizationInfo;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws BussException, AuthenticationException {
        String token = (String) auth.getCredentials();
        if (Strings.isNullOrEmpty(token)) {
            log.warn("token丢失");
            throw new BussException(BusCode.TOKEN_LOSE);
        }
        try {
            Claims claims = jwtUtil.getClaimFromToken(token);
            String userOrgId = claims.getSubject();
            String redisKey = CachePre.LOING_SHIRO_JWT_ID + token;
            log.info("userId : {}" , token);
            long expire = RedisUtil.getExpire(redisKey);
            if(!RedisUtil.hasKey(redisKey) && expire <= 0){
                throw new BussException(BusCode.TOKEN_EXPIRATION);
            }
            return new SimpleAuthenticationInfo(userOrgId, token, getName());
        } catch (ExpiredJwtException e) {
            log.warn("token过期:token:{}", token);
            throw new BussException(BusCode.TOKEN_EXPIRATION);
        } catch (JwtException e) {
            log.warn("token无效:token:{}", token);
            throw new BussException(BusCode.TOKEN_INVALID);
        }
    }
}
