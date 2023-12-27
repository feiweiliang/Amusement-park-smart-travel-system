package com.huawei.c4demo.controller;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.FavoriteVO;
import com.huawei.c4demo.pojo.LoginVO;
import com.huawei.c4demo.pojo.RegisterVO;
import com.huawei.c4demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ServerResponse login(@RequestBody LoginVO loginVO){
        return loginService.login(loginVO);
    }

    @PostMapping("/register")
    public ServerResponse register(@RequestBody RegisterVO registerVO){
        return loginService.register(registerVO);
    }
}
