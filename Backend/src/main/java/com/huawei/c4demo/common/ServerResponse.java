package com.huawei.c4demo.common;

/*
服务器响应给前台的统一格式
 */

import com.fasterxml.jackson.annotation.JsonInclude;

public class ServerResponse {
    /*status是一个数字表示这次请求的状态码，0成功，其他错误*/
    private Integer status;
    /*对此次结果的一个描述，例如：成功*/
    private  String message;
    /*给前台的数据*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    //考虑使用静态函数替代构造器，静态工厂方法，静态方法生成对象，好处是有名字
    public static ServerResponse success(){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(0);
        serverResponse.setMessage("成功");
        return serverResponse;
    }
    public static ServerResponse success(Object data){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(0);
        serverResponse.setMessage("成功");
        serverResponse.setData(data);
        return serverResponse;
    }
    public static ServerResponse success(String message,Object data){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(0);
        serverResponse.setMessage(message);
        serverResponse.setData(data);
        return serverResponse;
    }
    public static ServerResponse error(Integer status,String message){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(status);
        serverResponse.setMessage(message);
        return serverResponse;
    }
}