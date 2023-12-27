package com.huawei.c4demo.utils;


import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * API调用相关工具类
 */
public class ApiTools {

    private static final String url = "https://cn2.naas.huaweicloud.com:18002";

    /**
     * 获取与imaster对接获取北向帐号token信息
     *
     * @param userName 北向帐号
     * @param password 北向密码
     * @return 返回token信息
     */
    public static String getToken(String userName, String password) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url + "/controller/v2/tokens");
            request.addHeader("Content-Type", "application/json");
            String bodyData = "{" + "\"userName\":\"" + userName + "\"," + "\"password\":\"" + password + "\"}";
            request.setEntity(new StringEntity(bodyData, "UTF-8"));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        String data = jsonObject.get("data").toString();
        JSONObject dataJson = JSONObject.fromObject(data);

        return dataJson.get("token_id").toString();
    }

    /**
     * 创建AP站点
     *
     * @param token           北向token信息
     * @param siteName        站点名称
     * @param siteDescription 站点描述
     * @param latitude        纬度，北纬是正数，南纬是负数，比如北纬30.2度，传入30.2,；南纬50.76度，传入-50.76，纬度范围【-90，+90】
     * @param longtitude      经度，东经是正数，西经是负数，比如东经110度，传入110,；西经32.1度，传入-32.1，经度范围【-180，+180】
     * @param address         站点创建所在地址
     * @return 返回创建成功或创建失败的站点列表
     */
    public static String createSites(String token, String siteName, String siteDescription
            , String latitude, String longtitude, String address) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url + "/controller/campus/v3/sites");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("X-ACCESS-TOKEN", token);
            String bodyData = "{\n" +
                    "\t\"sites\": [{\n" +
                    "\t\t\"name\": \"" + siteName + "\",\n" +
                    "\t\t\"description\": \"" + siteDescription + "\",\n" +
                    "\t\t\"latitude\": \"" + latitude + "\",\n" +
                    "\t\t\"longtitude\": \"" + longtitude + "\",\n" +
                    "\t\t\"address\": \"" + address + "\"\n" +
                    "\t}]\n" +
                    "}";
            request.setEntity(new StringEntity(bodyData, "UTF-8"));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除站点
     *
     * @param token token信息
     * @param ids   删除站点列表
     * @return 返回删除成功或失败的站点ID列表
     */
    public static String deleteSites(String token, List<String> ids) {
        String result = null;
        List<String> bodyIds = new ArrayList<>();
        for (String id : ids) {
            bodyIds.add("\"" + id + "\"");
        }
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpDeleteWithBody request = new HttpDeleteWithBody(url + "/controller/campus/v3/sites");
//            HttpDelete request = new HttpDelete(url + "/controller/campus/v3/sites");
            request.addHeader("X-ACCESS-TOKEN", token);
            request.addHeader("Content-Type", "application/json");

            String bodyData = "{\n" +
                    "\t\"ids\": " + bodyIds + "\n" +
                    "}";

            request.setEntity(new StringEntity(bodyData, "UTF-8"));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询站点
     *
     * @param token token信息
     * @return 返回查询站点列表
     */
    public static String getSites(String token) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpDeleteWithBody request = new HttpDeleteWithBody(url + "/controller/campus/v3/sites");
            request.addHeader("X-ACCESS-TOKEN", token);
            request.addHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改AP站点信息
     *
     * @param token           北向token信息
     * @param siteId          站点id
     * @param siteName        站点名称
     * @param siteDescription 站点描述
     * @param latitude        纬度，北纬是正数，南纬是负数，比如北纬30.2度，传入30.2,；南纬50.76度，传入-50.76，纬度范围【-90，+90】
     * @param longtitude      经度，东经是正数，西经是负数，比如东经110度，传入110,；西经32.1度，传入-32.1，经度范围【-180，+180】
     * @param address         站点创建所在地址
     * @return 返回修改成功或创建失败的站点data结构
     */
    public static String modifySites(String token, String siteId, String siteName, String siteDescription
            , String latitude, String longtitude, String address) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut request = new HttpPut(url + "/controller/campus/v3/sites/" + siteId);
            request.addHeader("X-ACCESS-TOKEN", token);
            request.addHeader("Content-Type", "application/json");

            String bodyData = "{\n" +
                    "\t\"name\": \"" + siteName + "\",\n" +
                    "\t\"description\": \"" + siteDescription + "\",\n" +
                    "\t\"latitude\": \"" + latitude + "\",\n" +
                    "\t\"longtitude\": \"" + longtitude + "\",\n" +
                    "\t\"address\": \"" + address + "\"\n" +
                    "}";

            request.setEntity(new StringEntity(bodyData, "UTF-8"));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改站点AP增值服务配置
     *
     * @param token            token信息
     * @param siteId           站点ID。
     * @param enablePsk        PSK使能
     * @param mu               是否开启上传功能，将终端位置信息上报到指定服务器。
     * @param serverIp         服务器IP/域名
     * @param serverPort       端口号，必须为1-65535范围内的整数，不填默认为10031。
     * @param interver         上报周期，单位为ms，必须是500-60000范围内的整数，不填默认为20000。
     * @param rssi             阀值，必须为-95-0范围内的整数，不填默认为-75。
     * @param apLocationEnable 用于标识AP安装位置信息开关是否打开。
     * @param apLocation       AP安装位置信息。
     * @return 成功则返回站点内AP增值服务配置结构体。失败返回错误码
     */
    public static String modifyApExtraService(String token, String siteId, boolean enablePsk, boolean mu
            , String serverIp, Integer serverPort
            , Integer interver, Integer rssi, boolean apLocationEnable, String apLocation) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut request = new HttpPut(url + "/controller/campus/v1/networkservice/networkconfig/net/apextraservice/sites/"
                    + siteId + "/apextraservices");
            request.addHeader("X-ACCESS-TOKEN", token);
            request.addHeader("Content-Type", "application/json");

            String bodyData = "{\n" +
                    "\t\"enablePsk\": " + enablePsk + ",\n" +
                    "\t\"mu\": " + mu + ",\n" +
                    "\t\"serverIp\": \"" + serverIp + "\",\n" +
                    "\t\"serverPort\": " + serverPort + ",\n" +
                    "\t\"interver\": " + interver + ",\n" +
                    "\t\"rssi\": " + rssi + ",\n" +
                    "\t\"apLocationEnable\": " + apLocationEnable + ",\n" +
                    "\t\"apLocation\": \"" + apLocation + "\"\n" +
                    "}";

            request.setEntity(new StringEntity(bodyData, "UTF-8"));
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取站点内AP增值服务配置
     *
     * @param token  token信息
     * @param siteId 站点id
     * @return 返回站点内AP增值服务配置。
     */
    public static String getApExtraService(String token, String siteId) {
        String result = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url + "/controller/campus/v1/networkservice/networkconfig/net/apextraservice/sites/"
                    + siteId + "/apextraservices");
            request.addHeader("X-ACCESS-TOKEN", token);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
