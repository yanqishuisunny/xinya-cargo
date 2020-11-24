package com.cargo.menedata.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 梁建军
 * 创建日期： 2019/8/23
 * 创建时间： 18:43
 * @version 1.0
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    /**
     * 终结点
     */
    private String endpoint;
    /**
     * 访问密钥ID
     */
    private String accessKeyId;
    /**
     * 访问密钥机密
     */
    private String accessKeySecret;
    /**
     * BucketName
     */
    private String bucketName;
    /**
     * 访问域名 返回地址的时候拼接的地址
     * 例如 https://test-xsungroup.oss-cn-shanghai.aliyuncs.com/
     * 结尾
     */
    private String accessDomainName;
}
