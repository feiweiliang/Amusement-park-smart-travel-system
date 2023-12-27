package com.huawei.c4demo.controller;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    //返回地图信息
    @GetMapping("/getMap")
    public ServerResponse getMap(){return mapService.getMap();}

    //返回每个场所描述信息
    @GetMapping("/getGroundDesc/{id}")
    public ServerResponse getGroundDesc(@PathVariable(name = "id") Integer id){
        return mapService.getGroundDesc(id);
    }

    //返回每个场所预计等待时间
    @GetMapping("/getGroundWaitTime")
    public ServerResponse getGroundWaitTime(){
        return mapService.getGroundWaitTime();
    }
}
