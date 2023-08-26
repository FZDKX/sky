package com.fzdkx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 发着呆看星
 * @create 2023/8/26 22:00
 */
@ConfigurationProperties(prefix = "sky.baidu")
@Component
@Data
public class BaiDuProperties {
    private String shopAddress;
    private String ak;
}
