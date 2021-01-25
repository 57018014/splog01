package com.yy.controller;

import com.yy.bean.User;
import com.yy.operatorlog.PBUserOperatorLog;
import com.yy.scope.CurrentScope;
import com.yy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/25 13:30
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PBUserOperatorLog("根据ID查询用户信息")
    @GetMapping("/userById")
    public Object getUserById(@RequestParam("id") Long id){

        User user = userService.getUserById(id);
        return user;
    }
}
