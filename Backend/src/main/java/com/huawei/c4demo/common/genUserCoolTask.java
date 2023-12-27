package com.huawei.c4demo.common;

import com.huawei.c4demo.pojo.Point;
import com.huawei.c4demo.pojo.Usercoortab;
import com.huawei.c4demo.repository.UsercoortabRepository;
import com.huawei.c4demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class genUserCoolTask {
    @Autowired
    private UserService userService;
    @Autowired
    private UsercoortabRepository usercoortabRepository;

    //每过10秒，将园区内所有的终端mac地址和坐标持久化存储
//    @Scheduled(fixedRate = 10000)
//    private void genUserCool(){
//        HashMap<String, Point> allTerminalMacAndPoint = userService.getAllTerminalMacAndPoint();
//
//        allTerminalMacAndPoint.forEach((mac,point)->{
//            Usercoortab usercoortab = new Usercoortab();
//            usercoortab.setMac(mac);
//            usercoortab.setX(String.valueOf(point.getX()));
//            usercoortab.setY(String.valueOf(point.getY()));
//            usercoortabRepository.save(usercoortab);
//            usercoortabRepository.flush();
//            System.out.println("["+LocalDateTime.now()+"]  "+usercoortab.toString());
//        });
//    }

    }
