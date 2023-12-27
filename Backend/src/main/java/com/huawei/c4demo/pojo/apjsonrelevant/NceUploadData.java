package com.huawei.c4demo.pojo.apjsonrelevant;

import lombok.Data;

import java.util.List;

@Data
public class NceUploadData {
    //根据云平台上报数据格式创建entity类
    private List<ApUploadData> data;
    private String secret;
    private String type;

    @Data
    public static class ApUploadData {
        private String apMac;
        private List<TerminalData> terminalList;
    }

    @Data
    public static class TerminalData {
        private int rssi;
        private String terminalMac;
        private Long timestamp;
    }
}

