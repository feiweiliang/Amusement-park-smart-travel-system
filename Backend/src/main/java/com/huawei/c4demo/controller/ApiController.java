package com.huawei.c4demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.c4demo.pojo.apjsonrelevant.NceUploadData;
import com.huawei.c4demo.pojo.apjsonrelevant.ValidatorData;
import com.huawei.c4demo.utils.ApiTools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 与imaster位置服务对接对接
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private Environment env;

    //validator校验
    @RequestMapping (value = "/APjsonToJson", method = RequestMethod.GET)
    @ResponseBody
    public String  getValidator() throws JsonProcessingException {
        //在云平台请求时，返回数据格式为 {"validator":xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}
        //在此编码
        ValidatorData validatorData = new ValidatorData(env.getProperty("validator"));
        System.out.println("lbsGet():  " + validatorData);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(validatorData);
    }


    //将云平台上上报的终端位置信息解析并输出为json文件
    @RequestMapping(value = "/APjsonToJson", method = RequestMethod.POST)
    @ResponseBody
    public String apjsonToJson(@RequestBody String body) throws IOException {
        System.out.println("PostBody" + body);
        ObjectMapper mapper = new ObjectMapper();
        NceUploadData lbsEntity = mapper.readValue(body, NceUploadData.class);
        System.out.println(lbsEntity.getSecret() + "  vs  " + env.getProperty("secret"));

        String secret = lbsEntity.getSecret();

        //校验secret与预设是否相符
        if (!secret.equals(env.getProperty("secret"))) {
            return body;
        }
        //新建json文件
        File tempFile = new File("C:\\Users\\HUAWEI MateBook Xpro\\Desktop\\c4demoData\\" + new SimpleDateFormat("yyyyMMdd-HHmmss-SSSS").format(new Date()) + ".json");
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, true));

        bw.write(body);
        bw.newLine();
        bw.flush();
        bw.close();
        return body;
    }

}
