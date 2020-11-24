package com.commom.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author hlh
 */
public class JWTToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
