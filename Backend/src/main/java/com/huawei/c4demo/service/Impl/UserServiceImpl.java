package com.huawei.c4demo.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.*;
import com.huawei.c4demo.pojo.apjsonrelevant.APJson;
import com.huawei.c4demo.pojo.apjsonrelevant.APJsonDataInformation;
import com.huawei.c4demo.pojo.apjsonrelevant.APJsonTerminalList;
import com.huawei.c4demo.repository.*;
import com.huawei.c4demo.service.UserService;
import com.huawei.c4demo.utils.MyHashMap;
import com.huawei.c4demo.utils.Weather;
import com.huawei.c4demo.utils.UserPoint;
import com.huawei.c4demo.utils.UtilityClass;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApRepository apRepository;

    @Autowired
    private GroundRepository groundRepository;

    @Autowired
    private GroundusertabRepository groundusertabRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;


    private APJson apJson = UtilityClass.readJsonData("C:\\Users\\HUAWEI MateBook Xpro\\Desktop\\c4demoData\\c4demo_test.json");

    public ServerResponse getAllUsers() {
        return ServerResponse.success(userRepository.findAll());
    }

    public HashSet<String> getAllTerminalMac() {
        apJson = UtilityClass.readJsonData("C:\\Users\\HUAWEI MateBook Xpro\\Desktop\\c4demoData\\c4demo_test.json");
        ArrayList<APJsonDataInformation> datas = apJson.getData();
        HashSet<String> terminalMacSet = new HashSet<>();
        //获取每个AP中的data
        for (APJsonDataInformation data : datas) {
            for (APJsonTerminalList t : data.getApJsonTerminalList()) {
                //将AP检测到的终端mac存到集合
                terminalMacSet.add(t.getTerminalMac());
            }
        }
        return terminalMacSet;
    }

    @Override
    public ServerResponse getAllUsersNumber() {
        return ServerResponse.success(getAllTerminalMac().size());
    }

    @Override
    public HashMap<String, Point> getAllTerminalMacAndPoint() {
        //存放结果
        HashMap<String, Point> res = new HashMap<>();
        HashSet<String> allTerminalMac = getAllTerminalMac();
        //获得所有的AP信息
        ArrayList<Aptab> apss = (ArrayList<Aptab>) apRepository.findAll();

        Iterator<String> iterator = allTerminalMac.iterator();
        while (iterator.hasNext()) {
            String mac = iterator.next();
            //获取终端地址为mac的坐标(测试环境)
            String point = UserPoint.getUserPoint();
            String x = point.split(",")[0];
            String y = point.split(",")[1];

            //TODO 实际中，应该调用GetLocation类获取真实ip（生产环境）
//            Usercoortab uu = new Usercoortab();
//            uu.setId(1);
//            uu.setMac(mac);
//            GetLocation gl = new GetLocation();
//            Usercoortab user =gl.locateUser(apJson, uu, apss);
//            x = user.getX();
//            y = user.getY();
            res.put(mac, new Point(Float.parseFloat(x), Float.parseFloat(y)));
        }
        return res;
    }

    @Override
    public ServerResponse getPlacesUsersNumber() {


        List<HashMap<String, String>> res = new ArrayList<>();

        List<Groundtab> grounds = groundRepository.findAll();

        //遍历每一个场所
        for (Groundtab ground : grounds) {
            HashMap<String, String> resMap = new HashMap<>();
            String title = ground.getAttrTitle();
            String points = ground.getPoints();
            List<Point> pts = new ArrayList<>();
            //解析场所的几个顶点
            for (String p : points.split(" ")) {
                pts.add(new Point(Float.parseFloat(p.split(",")[0]), Float.parseFloat(p.split(",")[1])));
            }

            //获得所有坐标，判断有哪些坐标在场所内
            HashMap<String, Point> allTerminalMacAndPoint = getAllTerminalMacAndPoint();
            Integer num = new Integer(0);
            for (Point point : allTerminalMacAndPoint.values()) {
                //判断该用户是否在场所内
                Boolean isIn = UserPoint.pointIsIn(point, pts);
                num += isIn ? 1 : 0;
            }
            resMap.put("attrTitle", title);
            resMap.put("num", num.toString());
            res.add(resMap);
        }

        return ServerResponse.success(res);
    }

    @Override
    public ServerResponse getNumbersOfUserInLast14Days() {
        //获取当前时间
        long currentTimeMillis = System.currentTimeMillis();
        //格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> currentDate = new ArrayList<>();

        //时间和人数对应
        for (int i = 14; i >= 0; i--) {
            //计算出前14天的时间,一天的时间戳为86400000
            currentDate.add(formatter.format(currentTimeMillis - 86400000L * i) + " 00:00:00");
        }
        Map<String, Integer> numbersOfUserInLast14Days = new TreeMap<>();
        //计算出前14天的人数
        for (int i = 0; i < 14; i++) {
            //列出每天的终端mac
            ArrayList<String> userInLastTime = userRepository.getUserInLastTime(currentDate.get(i), currentDate.get(i + 1));
            //将终端mac数量和当前日期对应
            numbersOfUserInLast14Days.put(currentDate.get(i).split(" ")[0], userInLastTime.size());
        }
        return ServerResponse.success(numbersOfUserInLast14Days);
    }

    @Override
    public ServerResponse getNumbersOfUserInLast1Days(String specifyDate) {
        ArrayList<String> currentDate = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Integer> numbersOfUserInLast1Days = new TreeMap<>();
        try {
            long startTime = new SimpleDateFormat("yyyy-MM-dd").parse(specifyDate).getTime();
            //指定时间从8:00开始,一个小时时间戳为3600000
            startTime = startTime + 3600000 * 8;
            for (int i = 0; i <= 7; i++) {
                //计算出前8点到22点的时间,每两个小时算一次
                currentDate.add(formatter.format(startTime + 3600000L * i * 2));
            }

            for (int i = 0; i < 7; i++) {
                //计算出8点到22点时间段内的人数
                //列出每天的终端mac
                ArrayList<String> userInLastTime = userRepository.getUserInLastTime(currentDate.get(i), currentDate.get(i + 1));
                //将终端mac数量和当前日期对应
                String[] outputTime1 = currentDate.get(i).split(" ")[1].split(":");//当前时间
                String[] outputTime2 = currentDate.get(i + 1).split(" ")[1].split(":");//当前时间
                numbersOfUserInLast1Days.put(outputTime1[0] + ":" + outputTime1[1] + "-" + outputTime2[0] + ":" + outputTime2[1], userInLastTime.size());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ServerResponse.success(numbersOfUserInLast1Days);
    }

    @Override
    public ServerResponse getFavoriteByUserId(Integer id) {
        return ServerResponse.success(userRepository.getFavoriteByUserId(id));
    }

    @Override
    public ServerResponse setFavorite(Integer userId, List<Integer> groundIds) {
        try {
            //先查询用户的收藏列表，返回ground_id的数组
            List<Integer> oldGroundIds = favoriteRepository.getGroundIdByUserId(userId);
            List<Integer> newGroundIds = new ArrayList<>();
            groundIds.forEach(groundId -> {
                //过滤掉用户已经收藏的场所
                if (!oldGroundIds.contains(groundId)) {
                    newGroundIds.add(groundId);
                }
            });

            //String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()).split(" ")[0];
            newGroundIds.forEach(groundId -> {
                Favorite favorite = new Favorite();
                favorite.setUser_id(userId);
                //favorite.setCreate_time(createTime);
                favorite.setGround_id(groundId);
                favoriteRepository.save(favorite);
                favoriteRepository.flush();
            });
            return ServerResponse.success();
        } catch (Exception e) {
            return ServerResponse.error(1, e.toString());
        }
    }

    @Override
    public ServerResponse getPointByUserId(Integer id) {
        Usertab usertab = userRepository.findById(id).orElse(null);
        String userMac = usertab.getMac();

        HashMap<String, Point> allTerminalMacAndPoint = getAllTerminalMacAndPoint();

        return ServerResponse.success(allTerminalMacAndPoint.get(userMac));
    }


    /**
     * 获取到的当前场所内的用户数量，插入groundusertab表
     */
    @Override
    public void insertPlacesUsersNumber() {
        JSONObject resJson = JSONObject.fromObject(getPlacesUsersNumber());
        Map<String, Integer> dataMap = new HashMap<>();
        //获取到json里的data数据
        String data = resJson.get("data").toString();
        JSONArray dataArr = (JSONArray) JSONArray.parse(data);
        for (int i = 0; i < dataArr.size(); i++) {
            JSONObject dataArrJson = JSONObject.fromObject(dataArr.get(i));
            dataMap.put(dataArrJson.get("attrTitle").toString(), Integer.parseInt(dataArrJson.get("num").toString()));
        }
        JSONObject dataMapJson = JSONObject.fromObject(dataMap);
        Groundusertab groundusertab = new Groundusertab();
        groundusertab.setAttr_title_user_num(dataMapJson.toString());
        groundusertabRepository.save(groundusertab);
    }

    /**
     * 获取过去14天每个场所的用户数量
     */
    public Map<String, Integer> getPast14PlacesUsersNumber() {
        MyHashMap<String> numbersOfUserPast = new MyHashMap<>();
        List<Groundtab> grounds = groundRepository.findAll();

        //检查数据表中历史的用户数据，过去五天的用户数据
        //获取当前时间
        long currentTimeMillis = System.currentTimeMillis();
        //格式化时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> currentDate = new ArrayList<>();
        //计算出前14天的时间,一天的时间戳为86400000
        currentDate.add(formatter.format(currentTimeMillis) + " 00:00:00");//今天的时间
        currentDate.add(formatter.format(currentTimeMillis - 86400000L * 14) + " 00:00:00");//五天前的时间

        //计算出前14天场所人数
        List<Groundusertab> pastPlacesUsers = groundusertabRepository.getPastPlacesUsers(currentDate.get(1), currentDate.get(0));
        for (Groundusertab pastPlacesUser : pastPlacesUsers) {
            JSONObject attr_title_user_numJson = JSONObject.fromObject(pastPlacesUser.getAttr_title_user_num());
            for (Groundtab ground : grounds) {
                if (!"大门".equals(ground.getAttrTitle())) {
                    Integer usernum = (Integer) attr_title_user_numJson.get(ground.getAttrTitle());
                    numbersOfUserPast.put(ground.getAttrTitle(), usernum);
                }
            }
        }

        return numbersOfUserPast;
    }

    @Override
    public ServerResponse getTravelRecommend() {
        List<Groundtab> grounds = groundRepository.findAll();
        List<Favorite> favorites = favoriteRepository.findAll();

        Map<Integer, String> travelRecommendMap = new TreeMap<>();//存储最后推荐的数据
        MyHashMap<String> scorePlaceSum = new MyHashMap<>();//存储每个场所所得分数


        //获取游乐场当前地区天气情况
        JSONObject cityWeather = JSONObject.fromObject(Weather.getTodayWeather("北京市"));
        String dayWeather = (String) cityWeather.get("text_day");

        //获取前14天每个场所的用户数量，最终得出的是前14天所有到过的场所用户数量
        Map<String, Integer> pastPlacesUsersNumber = getPast14PlacesUsersNumber();

        String attrTitle;
        //输入天气情况，历史场所的用户数量，进行加权推荐
        for (Groundtab ground : grounds) {
            attrTitle = ground.getAttrTitle();
            if (!"大门".equals(attrTitle)) {//移除大门
                //根据天气情况增加推荐分数
                if (dayWeather.equals("晴") || dayWeather.equals("多云") || dayWeather.equals("阴")) {
                    //天气情况为晴、多云、阴  全部 +5
                    scorePlaceSum.put(attrTitle, 5);
                } else if (dayWeather.equals("阵雨") || dayWeather.equals("雷阵雨") || dayWeather.equals("雨夹雪")
                        || dayWeather.equals("小雨") || dayWeather.equals("中雨") || dayWeather.equals("小到中雨")) {
                    //阵雨、雷阵雨、雨夹雪、小雨、中雨、小到中雨;室内项目 +3
                    if (attrTitle.equals("欢乐小寨") || attrTitle.equals("星际飞碟") || attrTitle.equals("梦幻城堡")
                            || attrTitle.equals("玩偶对对碰") || attrTitle.equals("音乐餐厅") || attrTitle.equals("恐龙危机")) {
                        scorePlaceSum.put(attrTitle, 3);
                    }
                } else {//恶劣天气
                    travelRecommendMap.put(1, "不建议出行");
                    travelRecommendMap.put(2, "不建议出行");
                    travelRecommendMap.put(3, "不建议出行");
                    return ServerResponse.success(travelRecommendMap);
                }

                //前14天，根据定时任务使每天插入数据为12个小时，每个小时30次。
                double tmpPlacesUsersNumber = (double) pastPlacesUsersNumber.get(attrTitle) / (14 * 12 * 30);

                //根据用户数量增加推荐分数
                if (tmpPlacesUsersNumber <= 10) {
                    scorePlaceSum.put(attrTitle, 1);
                } else if (tmpPlacesUsersNumber > 10 && tmpPlacesUsersNumber <= 20) {
                    scorePlaceSum.put(attrTitle, 2);
                } else if (tmpPlacesUsersNumber > 20 && tmpPlacesUsersNumber <= 30) {
                    scorePlaceSum.put(attrTitle, 3);
                } else if (tmpPlacesUsersNumber > 30 && tmpPlacesUsersNumber <= 40) {
                    scorePlaceSum.put(attrTitle, 4);
                } else if (tmpPlacesUsersNumber > 40 && tmpPlacesUsersNumber <= 50) {
                    scorePlaceSum.put(attrTitle, 5);
                } else if (tmpPlacesUsersNumber > 50 && tmpPlacesUsersNumber <= 60) {
                    scorePlaceSum.put(attrTitle, 6);
                } else if (tmpPlacesUsersNumber > 60 && tmpPlacesUsersNumber <= 70) {
                    scorePlaceSum.put(attrTitle, 7);
                } else if (tmpPlacesUsersNumber > 70 && tmpPlacesUsersNumber <= 80) {
                    scorePlaceSum.put(attrTitle, 8);
                } else if (tmpPlacesUsersNumber > 80 && tmpPlacesUsersNumber <= 90) {
                    scorePlaceSum.put(attrTitle, 9);
                } else if (tmpPlacesUsersNumber > 90 && tmpPlacesUsersNumber <= 100) {
                    scorePlaceSum.put(attrTitle, 10);
                } else {
                    scorePlaceSum.put(attrTitle, 11);
                }
            }
        }

        MyHashMap<Integer> favoritesMap = new MyHashMap<>();//存储收藏相同场所的数量。例用户1与用户2同时收藏了3，则存储3:2
        //根据用户对场所的收藏数量来加权
        for (Favorite favorite : favorites) {//遍历收藏列表
            favoritesMap.put(favorite.getGround_id(), 1);//相同groundid就 + 1
        }
        //使2=3 变成 地动山摇=3
        List<Map.Entry<Integer, Integer>> favoritesMapEntry = new ArrayList<>(favoritesMap.entrySet());
        Map<String, Integer> tmpFavoriteHashMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> tmpFavoritesMapEntry : favoritesMapEntry) {
            tmpFavoriteHashMap.put(groundRepository.findAttrTitleById(tmpFavoritesMapEntry.getKey()), tmpFavoritesMapEntry.getValue());
        }
        //对tmpFavoriteHashMap里的场所进行逆序排序
        List<Map.Entry<String, Integer>> sortFavoriteHashMap = new ArrayList<>(tmpFavoriteHashMap.entrySet());
        sortFavoriteHashMap.sort((o1, o2) -> {
            return (o2.getValue() - (o1.getValue()));// o2 - o1表示逆序排序
        });

        int sortFavoriteHashMapLen = sortFavoriteHashMap.size();//需要相加的分数，以集合长度逐渐递减
        for (Map.Entry<String, Integer> tmpSortFavoriteHashMap : sortFavoriteHashMap) {//从
            scorePlaceSum.put(tmpSortFavoriteHashMap.getKey(), sortFavoriteHashMapLen);
            sortFavoriteHashMapLen--;
        }


        //对最终得分求top-3;对scorePastSum进行逆序排序
        List<Map.Entry<String, Integer>> listEntry = new ArrayList<>(scorePlaceSum.entrySet());
        listEntry.sort((o1, o2) -> {
            return (o2.getValue() - (o1.getValue()));// o2 - o1表示逆序排序
        });
        ArrayList<String> sortedString = new ArrayList<>();//按照已排好的顺序存放最终的数据
        for (Map.Entry<String, Integer> entry : listEntry) {
            sortedString.add(entry.getKey());
        }

        //返回推荐的数据
        travelRecommendMap.put(1, sortedString.get(0));
        travelRecommendMap.put(2, sortedString.get(1));
        travelRecommendMap.put(3, sortedString.get(2));

        return ServerResponse.success(travelRecommendMap);
    }

}
