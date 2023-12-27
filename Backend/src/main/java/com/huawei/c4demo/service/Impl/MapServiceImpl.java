package com.huawei.c4demo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.Groundtab;
import com.huawei.c4demo.repository.GroundRepository;
import com.huawei.c4demo.service.MapService;
import com.huawei.c4demo.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ObjectOutputStream;
import java.util.*;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private GroundRepository groundRepository;

    @Autowired
    private UserService userService;

    @Override
    public ServerResponse getMap(){

        ArrayList<TreeMap<String, Object>> resList = new ArrayList<>();
        for(Groundtab ground:groundRepository.findAll()){
            TreeMap<String, Object> groundMap = new TreeMap<>();

            groundMap.put("id",ground.getId());
            groundMap.put("points",ground.getPoints());
            groundMap.put("attrDataX",ground.getAttrDataX());
            groundMap.put("attrDataY",ground.getAttrDataY());
            groundMap.put("attrTitle",ground.getAttrTitle());

            TreeMap<String, String> typeMap = new TreeMap<>();
            typeMap.put("title",ground.getTitle());
            typeMap.put("fill",ground.getFill());
            typeMap.put("stroke",ground.getStroke());
            groundMap.put("type",typeMap);

            TreeMap<String, Object> connectMap = new TreeMap<>();
            connectMap.put("x",ground.getX());
            connectMap.put("y",ground.getY());
            ArrayList<String> connectBuildingsList = new ArrayList<>();
            connectBuildingsList.add(ground.getConnectBuildings());
            connectMap.put("connectBuildings",connectBuildingsList);
            groundMap.put("connectPoint",connectMap);

            resList.add(groundMap);
        }
        return ServerResponse.success(resList);
    }

    @Override
    public ServerResponse getGroundDesc(Integer id) {
        Groundtab grounddestab = groundRepository.findById(id).get();
        return ServerResponse.success(grounddestab.getDescription());
    }

    @Override
    public ServerResponse getGroundWaitTime() {
        JSONObject resJson = JSONObject.fromObject(userService.getPlacesUsersNumber());
        Map<String, Integer> dataMap = new HashMap<>();
        //获取到json里的data数据
        String data = resJson.get("data").toString();
        JSONArray dataArr = (JSONArray) JSONArray.parse(data);
        for (Object o : dataArr) {
            JSONObject dataArrJson = JSONObject.fromObject(o);
            dataMap.put(dataArrJson.get("attrTitle").toString(), Integer.parseInt(dataArrJson.get("num").toString()));
        }

        ArrayList<String> groundTitle = new ArrayList<>();
        ArrayList<Integer> groundWaitTime = new ArrayList<>();
        Map<String, ArrayList> waitMap = new HashMap<>();
        List<Groundtab> grounds = groundRepository.findAll();

        Integer curPlaceUserNum;
        int waitTime ;
        for (Groundtab ground : grounds) {
            String attrTitle = ground.getAttrTitle();
            if (!"大门".equals(attrTitle)){
                //每个场所游玩每个人需要的大致时间，每个项目一次能游玩多少人
                //当前用户数 - 一次能游玩的人数 * i <= 一次能游玩的人数。 得到的 i * 大致时间 = 预计等待时间
                curPlaceUserNum = dataMap.get(ground.getAttrTitle());
                int curUser = curPlaceUserNum - ground.getRun_user_num();
                if (curUser < 0) {
                    curUser = 0;
                }
                waitTime = (curUser / ground.getRun_user_num()) * ground.getRun_time();
                groundTitle.add(ground.getAttrTitle());
                groundWaitTime.add(waitTime);
            }
        }

//        ArrayList<Integer> arrayList = new ArrayList();
//        arrayList.add(10);
//        arrayList.add(25);
//        arrayList.add(31);
//        arrayList.add(14);
//        arrayList.add(5);
//        arrayList.add(16);
//        arrayList.add(9);
//        arrayList.add(28);
//        arrayList.add(37);
//        arrayList.add(18);
//        arrayList.add(51);
//        arrayList.add(13);
//        arrayList.add(0);
//        arrayList.add(41);
//        arrayList.add(19);


        waitMap.put("groundTitle",groundTitle);
        waitMap.put("waitTime",groundWaitTime);

        return ServerResponse.success(waitMap);
    }


}
