package com.huawei.c4demo.common;


import com.huawei.c4demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class insertPlacesUsersNumberTask {
    @Autowired
    private UserService userService;

//    @Scheduled(cron = "0 0/2 8,9,10,11,12,13,14,15,16,17,18,19,20,21,22 * * ?")//每天的8点-22点每2分钟执行一次
//    private void insertPlacesUsersNumber(){
//        userService.insertPlacesUsersNumber();
//    }
}
