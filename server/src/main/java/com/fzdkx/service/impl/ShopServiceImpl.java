package com.fzdkx.service.impl;

import com.fzdkx.service.ShopService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/20 19:35
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private StringRedisTemplate template;
    private static final String SHOP_STATUS = "shop:status";
    private static final String DEFAULT_STATUS = "0";

    @Override
    public Integer getStatus() {
        String statusStr = template.opsForValue().get(SHOP_STATUS);
        if (statusStr == null){
            statusStr = DEFAULT_STATUS;
            editStatus(0);
        }
        return Integer.valueOf(statusStr);
    }

    @Override
    public void editStatus(Integer status) {
        template.opsForValue().set(SHOP_STATUS,status.toString());
    }
}
