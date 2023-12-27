package com.huawei.c4demo.pojo.apjsonrelevant;

import java.util.ArrayList;

public class APJsonDataInformation {
    private String apMac;
    private ArrayList<APJsonTerminalList> apJsonTerminalList;

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public ArrayList<APJsonTerminalList> getApJsonTerminalList() {
        return apJsonTerminalList;
    }

    public void setApJsonTerminalList(ArrayList<APJsonTerminalList> apJsonTerminalList) {
        this.apJsonTerminalList = apJsonTerminalList;
    }

    @Override
    public String toString() {
        return "{" +
                "apMac='" + apMac + '\'' +
                ", terminalList=" + apJsonTerminalList +
                '}';
    }
}
