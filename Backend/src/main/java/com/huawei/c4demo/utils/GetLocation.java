package com.huawei.c4demo.utils;

import com.huawei.c4demo.pojo.Aptab;
import com.huawei.c4demo.pojo.Usercoortab;
import com.huawei.c4demo.pojo.apjsonrelevant.APJson;
import com.huawei.c4demo.pojo.apjsonrelevant.APJsonTerminalList;

import java.util.ArrayList;

public class GetLocation {

    public class tmpCoordinate {
        float x;
        float y;
        float d;
    }

    private static final int A = 50;
    private static final float n = 4F;


    public tmpCoordinate threePoint(tmpCoordinate tmpCo1, tmpCoordinate tmpCo2, tmpCoordinate tmpCo3) {
        float x = 0, y = 0;
        float[] ref_x = new float[3];
        float[] ref_y = new float[3];
        float[] ref_d = new float[3];
        float[] dxyx = new float[5 ] ;
        float[] dxyy = new float[5];
        float[] x_divide_y = new float[5];
        float[] y_divide_x = new float[5];
        float[] temp_x = new float[5];
        float[] temp_y = new float[5];
        int j = 0, k = 0;
        tmpCoordinate[] tmpCo = new tmpCoordinate[3];
        tmpCo[0] = tmpCo1;
        tmpCo[1] = tmpCo2;
        tmpCo[2] = tmpCo3;
        for (int i = 0; i < 3; ++i) {
            ref_x[i] = tmpCo[i].x;
            ref_y[i] = tmpCo[i].y;
            ref_d[i] = tmpCo[i].d;
        }
        for (int i = 0; i < 3; i++) {

            j = Math.min((i + 1), 2);
            k = k > 1 ? 0 : k;


            if (ref_x[j] - ref_x[k] != 0) {
                dxyx[i] = (ref_d[k] * ref_d[k] - ref_d[j] * ref_d[j] - ref_x[k] * ref_x[k] + ref_y[j] * ref_y[j] + ref_x[j] * ref_x[j] - ref_y[k] * ref_y[k]) / 2 / (ref_x[j] - ref_x[k]);
            }else {
                dxyx[i] = (float) 0;
            }

            if (ref_y[j] - ref_y[k] != 0) {
                dxyy[i] = (ref_d[k] * ref_d[k] - ref_d[j] * ref_d[j] - ref_x[k] * ref_x[k] + ref_y[j] * ref_y[j] + ref_x[j] * ref_x[j] - ref_y[k] * ref_y[k]) / 2 / (ref_y[j] - ref_y[k]);
            }else {
                dxyy[i] = (float) 0;
            }

            if (ref_y[j] - ref_y[k] != 0) {
                x_divide_y[i] = (ref_x[j] - ref_x[k]) / (ref_y[j] - ref_y[k]);
            }else {
                x_divide_y[i] = (float) 0;
            }

            if (ref_x[j] - ref_x[k] != 0) {
                y_divide_x[i] = (ref_y[j] - ref_y[k]) / (ref_x[j] - ref_x[k]);
            }else {
                y_divide_x[i] = (float) 0;
            }
            k++;
        }

        j = 0;
        k = 0;
        for (int i = 0; i < 3; i++) {
            j = Math.min((i + 1), 2);
            k = k > 1 ? 0 : k;
            if (x_divide_y[k] - x_divide_y[j] != 0) {
                temp_x[i] = (dxyy[k] - dxyy[j]) / (x_divide_y[k] - x_divide_y[j]);
                temp_y[i] = (dxyx[k] - dxyx[j]) / (y_divide_x[k] - y_divide_x[j]);
            } else {
                temp_x[i] = (float) 0;
                temp_y[i] = (float) 0;
            }
        }

        x = (temp_x[0] + temp_x[1] + temp_x[2]) / 3;
        y = (temp_y[0] + temp_y[1] + temp_y[2]) / 3;

        tmpCoordinate tco = new tmpCoordinate();
        tco.x = x;
        tco.y = y;
        return tco;

    }

    public Integer isPara(Integer a, Integer b, Integer c, ArrayList<tmpCoordinate> tmpCoord) {
        if (tmpCoord.get(a).x - tmpCoord.get(b).x == 0 || tmpCoord.get(a).x - tmpCoord.get(c).x == 0 || tmpCoord.get(b).x - tmpCoord.get(c).x == 0 ||
                tmpCoord.get(a).y - tmpCoord.get(b).y == 0 || tmpCoord.get(a).y - tmpCoord.get(c).y == 0 || tmpCoord.get(b).y - tmpCoord.get(c).y == 0) {
            return 1;
        }
        return 0;
    }

    public Usercoortab locateUser(APJson aps, Usercoortab uu, ArrayList<Aptab> apss) {
        ArrayList<tmpCoordinate> tmpCo = new ArrayList<>();
        ArrayList<tmpCoordinate> p = new ArrayList<>();
        ArrayList<APJsonTerminalList> tmpTermList = new ArrayList<>();
        int cnt = 0, k = 0;
        int[] vis = new int[1500];
        for (int i = 0; i < aps.getData().size(); ++i) {
            int tmpsize = 0;
            while (k < apss.size() && vis[k] == 0) {
                if (apss.get(k).getMac().equals(aps.getData().get(i).getApMac())) {
                    vis[k] = 1;
                    break;
                }
                k++;
            }
            while (tmpsize < aps.getData().get(i).getApJsonTerminalList().size()) {
                tmpTermList.add(aps.getData().get(i).getApJsonTerminalList().get(tmpsize));
                tmpsize++;
            }
            for (APJsonTerminalList apJsonTerminalList : tmpTermList) {
                if (uu.getMac().equals(apJsonTerminalList.getTerminalMac())) {
                    tmpCoordinate tmp = new tmpCoordinate();
                    tmp.x = apss.get(k).getX();
                    tmp.y = apss.get(k).getY();
                    tmp.d = (float) Math.pow(10, (Math.abs(apJsonTerminalList.getRssi()) - A) / (10 * n));
                    tmpCo.add(cnt, tmp);
                    cnt++;
                    break;  //一个ap里只要找到一个有关当前用户的mac信息就开始找下一个ap中是否包含
                }
            }
            tmpTermList.clear();
            k = 0;
        }
        //三个点才能定位
        if (cnt >= 2) {
            int j = 0 ,i ;
            int m = 3, n = cnt;
            Integer a[] = new Integer[3];
            for (i = 0; i < m; i++) {
                a[i] = i + 1;
            }

            for (j=m; a[0] <= (n - m + 1); ) {
                for (; a[m-1] <= n; a[m-1]++) {
                    // 将组合下标传入判断函数，返回0则表示此组合的三个参考点可以做为定位算法使用
                    if (0 == isPara((a[0] - 1), (a[1] - 1), (a[2] - 1), tmpCo)) {
                        tmpCoordinate tmc = new tmpCoordinate();
                        tmc = threePoint(tmpCo.get((a[0] - 1)), tmpCo.get(a[1] - 1), tmpCo.get(a[2] - 1));
                        p.add(tmc);
                    }
                }
                //判断a[1]--a[m-2]是否有进位 如果 a[m-1]>n 产生进位
                for (j = m - 2; j >= 0; j--) {
                    a[j]++;
                    //a[j]不进位,那么a[j-1]也不进位，结束继续判断
                    if (a[j] <= (j + n - m + 1)) {
                        break;
                    }
                }
                //调整，使得a[index-1],a[index],a[index]顺序排列，其中a[index]产生进位
                for (j++; j > 0 && j < m; j++) {
                    a[j] = a[j - 1] + 1;
                }
            }
        } else {
            System.out.println("无法定位");
        }

        float sum_x = 0, sum_y = 0, avg_x = 0, avg_y = 0;
        for (GetLocation.tmpCoordinate tmpCoordinate : p) {
            sum_x += tmpCoordinate.x;
            sum_y += tmpCoordinate.y;
        }
        if (p.size() > 0) {
            avg_x = sum_x / p.size();
            avg_y = sum_y / p.size();
            uu.setX(String.valueOf(avg_x));
            uu.setY(String.valueOf(avg_y));
            return uu;
        }else{
              return null;
        }
    }
}
