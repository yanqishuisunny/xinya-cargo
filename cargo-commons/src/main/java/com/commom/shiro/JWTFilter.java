package com.commom.shiro;

import com.commom.config.JwtProperties;
import com.commom.core.BusCode;
import com.commom.core.IBusCode;
import com.commom.exception.BussException;
import com.commom.utils.HttpServletUtil;
import com.commom.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * shiro token 过滤器
 * @author helihui
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private JwtProperties jwtProperties;




    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws RuntimeException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(jwtProperties.getHeader());
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login( new JWTToken(token));
        // 如果没有抛出异常则代表登入成功，返回true  抛出异常后  直接写出报错信息
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws RuntimeException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        log.info("shiro 拦截 URI = {}", uri);
        if(ignoreUri(uri)){
            //当为忽略验证的url，也会解析当前用户
            return true;
        }

        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            // 如果没有抛出异常则代表登入成功，返回true
            return executeLogin(request,response);
        } catch (AuthenticationException e) {
            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            IBusCode code = BusCode.TOKEN_EXPIRATION;
            if(e.getCause() instanceof BussException){
                BussException exception = (BussException)e.getCause();
                code = exception.getCode();
            }
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            HttpServletUtil.responseWrite(httpServletResponse, ResponseUtil.result(code));
            return false;
        }

    }

    /**
     * 忽略检查的uri
     * @param uri
     * @return true 需要登录 false 不需要登陆
     */
    private boolean ignoreUri(String uri) {
        List<String> excludeList = new ArrayList<>();
        List<String> list = jwtProperties.getFilterUris();
        if(!CollectionUtils.isEmpty(list)){
            excludeList.addAll(list);
        }

        boolean loginFlag = false;
        for (String exclude : excludeList) {
            if (uri.startsWith(exclude)) {
                loginFlag = true;
                break;
            }
        }
        return loginFlag;
    }


}
