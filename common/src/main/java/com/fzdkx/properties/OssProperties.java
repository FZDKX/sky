package com.fzdkx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 发着呆看星
 * @create 2023/8/17 11:42
 */
@ConfigurationProperties(prefix = "sky.oos")
@Component
@Data
public class OssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
