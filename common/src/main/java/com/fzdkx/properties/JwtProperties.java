package com.fzdkx.properties;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 发着呆看星
 * @create 2023/8/10 18:05
 */
@ConfigurationProperties(prefix = "sky.jwt")
@Component
@Data
public class JwtProperties {
    private String secureKey;
    private int ttl;
    private String tokenName;
}
