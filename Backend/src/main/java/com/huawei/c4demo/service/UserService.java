package com.huawei.c4demo.service;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface UserService {

    ServerResponse getAllUsers();

    //请求ImassterNCE,获得Ap信息，返回所有的终端MAC
    HashSet<String> getAllTerminalMac();

    //根据AP信息和终端MAC，计算所有终端MAC的坐标
    HashMap<String, Point> getAllTerminalMacAndPoint();

    //获得当前园区内所有用户数量
    ServerResponse getAllUsersNumber();
    //获得每个场所内用户数量
    ServerResponse getPlacesUsersNumber();

    //获取过去14天用户数量
    ServerResponse getNumbersOfUserInLast1Days(String specifyDate);
    //获取过去某一天中分时段用户数量
    ServerResponse getNumbersOfUserInLast14Days();

    //返回用户的收藏列表,需要一个用户id
    ServerResponse getFavoriteByUserId(Integer id);

    //设置用户的收藏，参数为：用户id,场所id列表，返回成功与失败
    ServerResponse setFavorite(Integer userId, List<Integer> groundIds);

    //参数为用户的id，返回用户坐标
    ServerResponse getPointByUserId(Integer id);

    //根据天气，历史游览用户数 推荐top-3游览项目
    ServerResponse getTravelRecommend();

    //不断插入数据进groundusertab表
    void insertPlacesUsersNumber();
}
