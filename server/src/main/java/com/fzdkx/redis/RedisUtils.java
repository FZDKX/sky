package com.fzdkx.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author 发着呆看星
 * @create 2023/8/22 23:39
 */
@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate template;

    public void deleteKey(String keyPrefix , String keyValue) {
        if (keyValue != null){
            template.delete(keyPrefix + keyValue);
        }
        Set<String> keys = template.keys(keyPrefix+"*");
        template.delete(keys);
    }
}
