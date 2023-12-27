package com.huawei.c4demo.controller;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/travel")
public class RecommendController {

    @Autowired
    private UserService userService;

    @GetMapping("/getTravelRecommend")
    public ServerResponse getTravelRecommend(){
        return userService.getTravelRecommend();
    }

}
