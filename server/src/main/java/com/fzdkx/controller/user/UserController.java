package com.fzdkx.controller.user;

import com.fzdkx.dto.UserLoginDTO;
import com.fzdkx.result.Result;
import com.fzdkx.service.UserService;
import com.fzdkx.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/21 10:14
 */
@RestController("userUserController")
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout(){
        userService.wxLogout();
        return Result.success();
    }
}
