package com.huawei.c4demo.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.FavoriteVO;
import com.huawei.c4demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ServerResponse getAllUser() {
        return userService.getAllUsers();
    }

    //返回当前游乐场用户总数
    @GetMapping("/getAllUsersNumber")
    public ServerResponse getAllUsersNumber(){return userService.getAllUsersNumber();}

    //返回每个场所中的用户数量
    @GetMapping("/getPlacesUsersNumber")
    public ServerResponse getPlacesUsersNumber(){return userService.getPlacesUsersNumber();}

    //近14天园区人数
    @GetMapping("/getNumbersOfUserInLast14Days")
    public ServerResponse getNumbersOfUserInLast14Days() {
        return userService.getNumbersOfUserInLast14Days();
    }

    //指定某一天分时段园区人数
    @PostMapping ("/getNumbersOfUserInLast1Days")
    public ServerResponse getNumbersOfUserInLast1Days(@RequestBody String specifyDate) {
        JsonObject specifyDateJson = (JsonObject)JsonParser.parseString(specifyDate);
        return userService.getNumbersOfUserInLast1Days(specifyDateJson.get("specifyDate").getAsString());
    }

    //返回用户的收藏列表,需要一个用户id
    @GetMapping("/getFavorite/{id}")
    public ServerResponse getFavorite(@PathVariable("id") Integer id){
        return userService.getFavoriteByUserId(id);
    }

    //设置用户的收藏，post方法请求，需要一个用户id，一个ground_id的整型数组，返回成功与失败
    @PostMapping ("/setFavorite")
    public ServerResponse setFavorite(@RequestBody FavoriteVO favoriteVO){
        return userService.setFavorite(favoriteVO.getUserId(),favoriteVO.getGroundIds());
    }

    //根据用户id，返回用户当前的坐标
    @GetMapping("/getPointByUserId/{id}")
    public ServerResponse getPointByUserId(@PathVariable("id") Integer id){
        return userService.getPointByUserId(id);
    }

}
