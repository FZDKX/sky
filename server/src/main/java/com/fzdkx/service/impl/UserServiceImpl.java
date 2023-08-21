package com.fzdkx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzdkx.constant.MessageConstant;
import com.fzdkx.entity.User;
import com.fzdkx.exception.LoginException;
import com.fzdkx.exception.LogoutException;
import com.fzdkx.exception.UserNotFoundException;
import com.fzdkx.mapper.UserMapper;
import com.fzdkx.properties.JwtProperties;
import com.fzdkx.properties.WeChatProperties;
import com.fzdkx.service.UserService;
import com.fzdkx.utils.HttpClientUtil;
import com.fzdkx.utils.JwtUtil;
import com.fzdkx.utils.UserThreadLocal;
import com.fzdkx.vo.UserLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.fzdkx.constant.MessageConstant.LOGOUT_ERROR;
import static com.fzdkx.constant.RedisConstant.REDIS_USER_TOKEN_PRE;

/**
 * @author 发着呆看星
 * @create 2023/8/21 13:01
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Resource
    private WeChatProperties weChatProperties;
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private JwtProperties jwtProperties;

    @Override
    public UserLoginVO wxLogin(String code) {
        if (code == null || code.equals("")){
            throw  new UserNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        // 获取openId
        String json = getOpenId(code);
        JSONObject jsonObject = JSON.parseObject(json);
        String openId = jsonObject.getString("openid");
        // 判断openId是否为null
        if (openId == null || openId.equals("")){
            throw new LoginException(MessageConstant.LOGIN_FAILED);
        }

        // 利用openID查询数据库
        User user = userMapper.selectUserByOpenid(openId);

        // 如果是新用户
        if (user == null){
            user  = User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        // 生成token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("id",user.getId().toString());
        tokenMap.put("openId",openId);
        String token = jwtUtil.createToken(tokenMap);

        // 存储至Redis
        template.opsForValue().set(REDIS_USER_TOKEN_PRE+user.getId(),token,jwtProperties.getTtl(), TimeUnit.HOURS);

        // 封装返回数据
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user,userLoginVO);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    @Override
    public void wxLogout() {
        Boolean flag = template.delete(REDIS_USER_TOKEN_PRE + UserThreadLocal.getId());
        if (flag == null || !flag){
            throw new LogoutException(LOGOUT_ERROR);
        }
    }

    public String getOpenId(String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        return HttpClientUtil.doGet(WX_LOGIN, map);
    }
}
