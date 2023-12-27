package com.huawei.c4demo.service.Impl;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.Groundtab;
import com.huawei.c4demo.pojo.Point;
import com.huawei.c4demo.pojo.Roadtab;
import com.huawei.c4demo.pojo.Usercoortab;
import com.huawei.c4demo.repository.GroundRepository;
import com.huawei.c4demo.repository.RoadRepository;
import com.huawei.c4demo.repository.UserRepository;
import com.huawei.c4demo.service.PathService;
import com.huawei.c4demo.service.UserService;
import com.huawei.c4demo.utils.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathServiceImpl implements PathService {

    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroundRepository groundRepository;

    @Autowired
    private UserRepository userRepository;

    class Road {
        float distance;
        int id;
        List<Integer> nextId;
    }

    class tmpRoad {
        float dis;
        int flag;
        List<Point> tmpPoint;

        tmpRoad() {
            flag = 0;
            tmpPoint = new ArrayList<>();
        }
    }

    private List<Roadtab> roadtabs = new ArrayList<>();
    private Integer[] vis = new Integer[20000];
    private Integer end;

    public float getDis(Roadtab r) {
        float dis = 0;
        dis = (float) Math.sqrt(Math.pow(r.getX1() - r.getX2(), 2) + Math.pow(r.getY1() - r.getY2(), 2));
        return dis;
    }

    public Integer getVisId(int i) {
        for (int tmp = 0; tmp < roadtabs.size(); tmp++) {
            if (roadtabs.get(tmp).getId() == i)
                return tmp;
        }
        return 0;
    }


    @Override
    public ServerResponse getDensityByPathId(Integer pathId) {
        //查询该路径的信息
        Roadtab roadtab = roadRepository.findById(pathId).orElse(null);
        //以路径的两个坐标为基准，画一个平行四边形，pts是平行四边形的顶点
        List<Point> pts = new ArrayList<>();
        float x1 = roadtab.getX1();
        float x2 = roadtab.getX2();
        float y1 = roadtab.getY1();
        float y2 = roadtab.getY2();
        pts.add(new Point(x1 + 1, y1 - 1));
        pts.add(new Point(x1 - 1, y1 + 1));
        pts.add(new Point(x2 + 1, y2 - 1));
        pts.add(new Point(x2 - 1, y2 + 1));

        //获得当前所有用户的坐标，计算这个矩形中有多少用户
        Integer num = new Integer(0);
        Iterator<String> iterator = userService.getAllTerminalMac().iterator();
        while (iterator.hasNext()) {
            String mac = iterator.next();
            //获取终端地址为mac的坐标(测试环境)
            String point = UserPoint.getUserPoint();
            String x = point.split(",")[0];
            String y = point.split(",")[1];

            //实际中，应该获取真实ip（生产环境）
//            Usercoortab uu = new Usercoortab();
//            uu.setId(1);
//            uu.setMac(mac);
//            GetLocation gl = new GetLocation();
//            Usercoortab user =gl.locateUser(apJson, uu, apss);
//            x = user.getX();
//            y = user.getY();
//            System.out.println("坐标"+x+","+y);

            //判断该用户是否在平行四边形内
            Boolean isIn = UserPoint.pointIsIn(new Point(Float.parseFloat(x), Float.parseFloat(y)), pts);
            num += isIn ? 1 : 0;
        }


        //平行四边形分割成两个三角形，用海伦公式求面积S▲=√p(p-a)(p-b)(p-c)
        Double t = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        float a = Float.parseFloat(t.toString());
        t = Math.sqrt(8);
        float b = Float.parseFloat(t.toString());
        t = Math.sqrt((x1 - x2 + 2) * (x1 - x2 + 2) + (y1 - y2 - 2) * (y1 - y2 - 2));
        float c = Float.parseFloat(t.toString());
        float p = (a + b + c) / 2;

        t = 2 * Math.sqrt(p * (p - a) * (p - b) * (p - c));
        float s = Float.parseFloat(t.toString());

        //密度=人数/平行四边形面积,为了方便后面使用，把他*1000并转为整型
        float density = (float) num / s;
        System.out.println(s);
        System.out.println(num);
        return ServerResponse.success((int) (density * 10000));
    }

    @Override
    public Integer getNearestRoad(Point point) {
        Integer pathId = new Integer(-1);
        float shortestDistance = 1000000000;

        float x0 = point.getX();
        float y0 = point.getY();

        List<Roadtab> allRaod = roadRepository.findAll();
        for (Roadtab road : allRaod) {
            float x1 = road.getX1();
            float x2 = road.getX2();
            float y1 = road.getY1();
            float y2 = road.getY2();

            Double t = pointToLine(x1, y1, x2, y2, x0, y0);
            float distance = Float.parseFloat(t.toString());

            if (distance <= shortestDistance) {
                shortestDistance = distance;
                pathId = road.getId();
            }
        }

        return pathId;
    }

    //计算点(x0,y0)到线段<(x1,y1),(x2,y2)>的距离
    private double pointToLine(float x1, float y1, float x2, float y2, float x0,

                               float y0) {

        double space = 0;

        double a, b, c;

        a = lineSpace(x1, y1, x2, y2);// 线段的长度

        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离

        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离

        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }

        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }

        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离(利用三角形面积公式求高)
        return space;
    }

    // 计算两点之间的距离
    private double lineSpace(float x1, float y1, float x2, float y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
                * (y1 - y2));
        return lineLength;
    }

    Integer rescnt = 0;
    public void getRoute(int start, List<Road> rds, int ii, tmpRoad[] tR,tmpRoad[] res) {

        if (start == getVisId(end)) {
            res[rescnt] = new tmpRoad();
            tR[ii].flag = 1;
            Point p = new Point(roadtabs.get(start).getX1(), roadtabs.get(start).getY1());
            Point pp = new Point(roadtabs.get(start).getX2(), roadtabs.get(start).getY2());
//            tR[ii].tmpPoint.add(p);
//            tR[ii].tmpPoint.add(pp);
            tR[ii].dis += rds.get(start).distance;
            for(Point tp: tR[ii].tmpPoint)
                res[rescnt].tmpPoint.add(tp);
            res[rescnt].dis = tR[ii].dis;
            rescnt++;
            return;

        }
        int roadId = start;
        float tmp = tR[ii].dis;
        List<Point> tmpP = new ArrayList<>();
        for (Point tx: tR[ii].tmpPoint){
            tmpP.add(tx);
        }
        tR[roadId].dis = tmp;
        tR[roadId].tmpPoint.clear();
        for (Point t: tmpP){
            tR[roadId].tmpPoint.add(t);
        }
        Point p = new Point(roadtabs.get(roadId).getX1(), roadtabs.get(roadId).getY1());
        Point pp = new Point(roadtabs.get(roadId).getX2(), roadtabs.get(roadId).getY2());
        if (tR[roadId].tmpPoint.contains(p) && tR[roadId].tmpPoint.contains(pp)){
            vis[roadId] = 1;
            return;
        }else {
            tR[roadId].tmpPoint.add(p);
            tR[roadId].tmpPoint.add(pp);
//           乘上拥挤程度 * Float.parseFloat(String.valueOf(getDensityByPathId(roadId)))
            tR[roadId].dis += rds.get(roadId).distance ;

            List<Point> tP = new ArrayList<>();
            for (Point trx: tR[roadId].tmpPoint){
                tP.add(trx);
            }
            vis[roadId] = 1;
            for (int i = 0; i < rds.get(roadId).nextId.size(); i++) {
                int ind = rds.get(roadId).nextId.get(i);
                if (vis[getVisId(ind)] == 1) {
                    continue;
                }
                getRoute(getVisId(ind), rds, roadId, tR,res);
                vis[getVisId(ind)] = 0;
                tR[getVisId(ind)].dis = 0;
                tR[getVisId(ind)].tmpPoint.clear();
            }
            vis[roadId] = 0;
            tR[roadId].dis = tmp;
            tR[roadId].tmpPoint.clear();
            for(Point ttt : tmpP){
                tR[roadId].tmpPoint.add(ttt);
            }
        }
        return;
    }

    @Override
    public ServerResponse getRoutePlan(Integer uuid, String proName) {

        tmpRoad[] tR = new tmpRoad[20000];
        tmpRoad[] res = new tmpRoad[20000];
        for(int i = 0; i < tR.length; i++)
            tR[i] = new tmpRoad();

        roadtabs = roadRepository.findAll();
        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < roadtabs.size(); i++) {
            Road rrd = new Road();
            rrd.distance = getDis(roadtabs.get(i));
            rrd.id = roadtabs.get(i).getId();
            rrd.nextId = new ArrayList<>();
            for (Roadtab rr : roadtabs) {
                if ((rr.getX1() == roadtabs.get(i).getX1() && rr.getY1() == roadtabs.get(i).getY1()) ||
                        (rr.getX1() == roadtabs.get(i).getX2() && rr.getY1() == roadtabs.get(i).getY2()) ||
                        (rr.getX2() == roadtabs.get(i).getX2() && rr.getY2() == roadtabs.get(i).getY2()) ||
                        (rr.getX2() == roadtabs.get(i).getX1() && rr.getY2() == roadtabs.get(i).getY1())) {
                    if (rr.getId() != rrd.id) {
                        rrd.nextId.add(rr.getId());
                    }
                }

            }
            roads.add(rrd);
        }

//        List<Usercoortab> ucs = userRepository.findUUID(uuid);
        //Float.parseFloat(ucs.get(0).getX()),Float.parseFloat(ucs.get(0).getY())
        Point upoint = new Point(500,933);
        int start = getNearestRoad(upoint);//离人最近的roadId
        int routeid = getVisId(start);

        List<Groundtab> grounds = new ArrayList<>();
        grounds = groundRepository.findAll();

        for (Groundtab gg : grounds) {
            if (gg.getAttrTitle().equals(proName)) {
                Point gPoint = new Point(gg.getX(),gg.getY());
                end = getNearestRoad(gPoint);
            }
        }
//        System.out.println(end);



        for (int i = 0; i < roads.get(routeid).nextId.size(); i++) {
            for (int j = 0; j < 1500; j++)
                vis[j] = 0;

            int index = 0;
            index = getVisId(roads.get(routeid).nextId.get(i));
            vis[routeid] = 1;
            Point p = new Point(roadtabs.get(routeid).getX1(), roadtabs.get(routeid).getY1());
            Point pp = new Point(roadtabs.get(routeid).getX2(), roadtabs.get(routeid).getY2());
            tR[index].tmpPoint.add(p);
            tR[index].tmpPoint.add(pp);
            tR[index].dis += roads.get(routeid).distance;
            getRoute(getVisId(roads.get(routeid).nextId.get(i)), roads, index, tR, res);
        }


        Point sp = new Point(roadtabs.get(routeid).getX1(),roadtabs.get(routeid).getY1());
        Point spp = new Point(roadtabs.get(routeid).getX2(),roadtabs.get(routeid).getY2());
        float min = Float.MAX_VALUE;
        int i = 0, index = 0;

        while (res[i] != null) {
            if (res[i].tmpPoint.get(0).equals(sp) && res[i].tmpPoint.get(1).equals(spp)){
//                System.out.println(res[i].tmpPoint);
//                System.out.println(res[i].dis);
                if (min > res[i].dis) {
                    min = res[i].dis;
                    index = i;
                }
            }
            i++;
        }
        for (int x = 0; x < vis.length; x++){
            vis[x] = 0;
        }
        rescnt = 0;
        System.out.println(min);
        List<Point> resPoint = new ArrayList<>();
        resPoint = res[index].tmpPoint;
        LinkedHashSet<Point> hashP = new LinkedHashSet<>(resPoint);
        List<Point> tmP = new ArrayList<>(hashP);

        List<List<Point>> resP = new ArrayList<>();
        resP.add(tmP);
        System.out.println(resP);
        return ServerResponse.success(resP);
    }


}
