package com.huawei.c4demo.pojo.apjsonrelevant;

public class APJsonTerminalList {
    private String terminalMac;
    private Integer rssi;
    private Long timestamp;

    public String getTerminalMac() {
        return terminalMac;
    }

    public void setTerminalMac(String terminalMac) {
        this.terminalMac = terminalMac;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "terminalMac='" + terminalMac + '\'' +
                ", rssi=" + rssi +
                ", timestamp=" + timestamp +
                '}';
    }
}
