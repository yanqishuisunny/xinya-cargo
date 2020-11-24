package com.commom.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * jwt相关配置
 *
 * @author Carlos
 * @date 2020-2-25 9:23
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = JwtProperties.JWT_PREFIX)
public class JwtProperties {

    public static final String JWT_PREFIX = "jwt";

    private String header;
    private String secret;
    private Long expiration;
    private Long refreshExpiration;
    private List<String> filterUris = new ArrayList<>();


}
