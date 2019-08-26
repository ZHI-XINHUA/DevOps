package com.swagger.springbootswagger.controller;

import com.swagger.springbootswagger.model.User;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Api(tags = "UserController（用户接口）",description = "用户操作相关接口")
public class UserController {

    @GetMapping("{userId}")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id",dataType = "string",paramType = "header",defaultValue = "zxh",example = "lisi")
    })
    public User getUser(@PathVariable String userId){
        User user = new User();
        user.setId(userId);
        user.setName("zxh");
        user.setAge(19);
        user.setSex("man");
        return user;
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加用户")
    public String addUser(User user){
        return user.toString();
    }

    @DeleteMapping("{userId}")
    @ResponseBody
    @ApiOperation(value = "删除用户")
    public String  deleteUser(@PathVariable String userId){
        return "删除"+userId+"成功";
    }


}
