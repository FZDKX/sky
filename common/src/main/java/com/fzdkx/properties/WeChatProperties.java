package com.fzdkx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 发着呆看星
 * @create 2023/8/21 13:23
 */
@ConfigurationProperties(prefix = "sky.wechat")
@Component
@Data
public class WeChatProperties {
    private String appid;
    private String secret;
}
