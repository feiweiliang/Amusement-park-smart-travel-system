package com.huawei.c4demo.pojo.apjsonrelevant;

import java.util.ArrayList;

public class APJson {
    private String secret;
    private String type;
    private ArrayList<APJsonDataInformation> data;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<APJsonDataInformation> getData() {
        return data;
    }

    public void setData(ArrayList<APJsonDataInformation> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "APJson{" +
                "secret='" + secret + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
