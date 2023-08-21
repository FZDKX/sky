package com.fzdkx.controller.user;

import com.fzdkx.result.Result;
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

    @PostMapping("/login")
    public Result login(@RequestBody String code){

        return Result.success();
    }
}
