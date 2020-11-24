package com.cargo.menedata.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author 梁建军
 * 创建日期： 2019/8/23
 * 创建时间： 18:43
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private OssProperties ossProperties;


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public OSS ossClient() {
        OSS ossClient = new OSSClientBuilder()
                .build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        return ossClient;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        ossClient().shutdown();
    }
}
