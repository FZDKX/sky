package com.fzdkx.config;

import com.fzdkx.properties.OssProperties;
import com.fzdkx.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 发着呆看星
 * @create 2023/8/17 11:56
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(OssProperties ossProperties) {
        log.info("开始创建阿里云文件上传工具类对象：{}",ossProperties);
        return new AliOssUtil(ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                ossProperties.getBucketName());
    }
}
