package com.huawei.c4demo.utils;

import com.google.gson.*;
import com.huawei.c4demo.pojo.apjsonrelevant.APJson;
import com.huawei.c4demo.pojo.apjsonrelevant.APJsonDataInformation;
import com.huawei.c4demo.pojo.apjsonrelevant.APJsonTerminalList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class UtilityClass {

    /**
     * 读取iMaster NCE-Campus通过https post方法上传的请求体中的终端位置json格式数据
     * @param fileName 文件路径
     * @return APjson
     */
    public static APJson readJsonData(String fileName) {
        APJson apJson = new APJson();
        try {

            JsonObject object = (JsonObject)JsonParser.parseReader(new FileReader(fileName));
            //读取对象内容
//            System.out.println("secret=" + object.get("secret").getAsString());
            apJson.setSecret(object.get("secret").getAsString());
//            System.out.println("type=" + object.get("type").getAsString());
            apJson.setType(object.get("type").getAsString());

            //读取data数组
            JsonArray arr = object.get("data").getAsJsonArray();

            ArrayList<APJsonDataInformation> apJsonDataInformationArr = new ArrayList<>();

            //data数组转换为json
            for (int i = 0; i < arr.size(); i++) {
                JsonObject data = arr.get(i).getAsJsonObject();
                APJsonDataInformation apJsonDataInformation = new APJsonDataInformation();
                apJsonDataInformation.setApMac(data.get("apMac").getAsString());
//                System.out.println("apMac=" + data.get("apMac").getAsString());
                //josn中读取terminalList数组
                JsonArray terminalList = data.get("terminalList").getAsJsonArray();
                ArrayList<APJsonTerminalList> apJsonTerminalListArr = new ArrayList<>();
                for (int j = 0; j < terminalList.size(); j++) {
                    APJsonTerminalList apJsonTerminalList = new APJsonTerminalList();
//                    System.out.println("=====" + i + "=====" + j);
                    JsonObject subObject = terminalList.get(j).getAsJsonObject();
                    apJsonTerminalList.setTerminalMac(subObject.get("terminalMac").getAsString());
                    apJsonTerminalList.setRssi(subObject.get("rssi").getAsInt());
                    apJsonTerminalList.setTimestamp(subObject.get("timestamp").getAsLong());
//                    System.out.println("terminalMac=" + subObject.get("terminalMac").getAsString());
//                    System.out.println("rssi=" + subObject.get("rssi").getAsInt());
//                    System.out.println("timestamp=" + sdf.parse(sdf.format(subObject.get("timestamp").getAsLong())));
                    apJsonTerminalListArr.add(apJsonTerminalList);
                }
                apJsonDataInformation.setApJsonTerminalList(apJsonTerminalListArr);
                apJsonDataInformationArr.add(apJsonDataInformation);
            }
            apJson.setData(apJsonDataInformationArr);
            System.out.println(apJson);

        } catch ( JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return apJson;
    }

}
