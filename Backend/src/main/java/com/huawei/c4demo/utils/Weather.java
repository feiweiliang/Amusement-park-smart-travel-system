package com.huawei.c4demo.utils;

import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Weather {
    public static Map<String, Object> getTodayWeather(String cityName) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet("http://portalweather.comsys.net.cn/weather03/api/weatherService/getDailyWeather?cityName=" + cityName);
            HttpResponse response = client.execute(request);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                String strResult = EntityUtils.toString(response.getEntity());

                // 处理数据，封装为map形式返回
                JSONObject resJson = JSONObject.fromObject(strResult);
                String results = resJson.get("results").toString();
                JSONArray resArray = (JSONArray) JSONArray.parse(results);
                JSONObject jsob = JSONObject.fromObject(resArray.get(0).toString());
                JSONArray zrray = (JSONArray) JSONArray.parse(jsob.get("daily").toString());
                Map<String, Object> map = (Map<String, Object>) zrray.get(0);
                int value = LocalDate.now().getDayOfWeek().getValue(); //周几
                map.put("today_week", getWeekByIntValue(value));
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeekByIntValue(int value) {
        String today = "星期";
        if (value == 1) {
            today = today + "一";
        } else if (value == 2) {
            today = today + "二";
        } else if (value == 3) {
            today = today + "三";
        } else if (value == 4) {
            today = today + "四";
        } else if (value == 5) {
            today = today + "五";
        } else if (value == 6) {
            today = today + "六";
        } else if (value == 7) {
            today = today + "天";
        }
        return today;
    }
}
