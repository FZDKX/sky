package com.fzdkx.controller.user;

import com.fzdkx.dto.UserLoginDTO;
import com.fzdkx.result.Result;
import com.fzdkx.service.UserService;
import com.fzdkx.vo.UserLoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/21 10:14
 */
@RestController()
@RequestMapping("/user/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        UserLoginVO userLoginVO = userService.wxLogin(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }
}
