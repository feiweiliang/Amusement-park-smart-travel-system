package com.huawei.c4demo.controller;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.Point;
import com.huawei.c4demo.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/path")
public class PathController {
    @Autowired
    private PathService pathService;

    //请求一个路径id，返回该路径的人流密度
    @GetMapping("/getDensityByPathId/{id}")
    public ServerResponse getDensityByPathId(@PathVariable("id") Integer pathId){
        return pathService.getDensityByPathId(pathId);
    }

    //测试getNearestRoad
    @GetMapping("/getNearestRoad/{x}/{y}")
    public ServerResponse getNearestRoad(@PathVariable("x") float x,@PathVariable("y") float y){
        return ServerResponse.success(pathService.getNearestRoad(new Point(x,y)));
    }

    @GetMapping("/getRoutePlan/{uuid}/{programName}")
    public ServerResponse getRoutePlan(@PathVariable("uuid")int uuid,@PathVariable("programName")String proName){

//        List<Point> resPoint = new ArrayList<>();
//        Point p = new Point(660.5F,933.0F);
//        Point pp = new Point(500F,933.0F);
//        resPoint.add(p);
//        resPoint.add(pp);
//        List<List<Point>> res = new ArrayList<>();
//        res.add(resPoint);
//        System.out.println(res);
//        return ServerResponse.success(res);


        return pathService.getRoutePlan(uuid, proName);
    }

}
