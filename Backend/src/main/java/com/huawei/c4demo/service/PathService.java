package com.huawei.c4demo.service;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.Point;

public interface PathService {


    //参数：路径id，返回该路径的人流密度（为方便后续使用，计算密度后*10000返回）
    ServerResponse getDensityByPathId(Integer pathId);

    //参数：一个坐标点，返回距离该坐标点最近的一条道路ID
    Integer getNearestRoad(Point point);

    ServerResponse getRoutePlan(Integer uuid, String proName);

}
