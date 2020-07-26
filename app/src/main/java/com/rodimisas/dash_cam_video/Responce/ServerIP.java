package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerIP implements Serializable {
    private int code;
    private String msg;
    @SerializedName("data")
    private ServerIpData ipData;

    public ServerIP() {
    }

    public ServerIP(int code, String msg, ServerIpData ipData) {
        this.code = code;
        this.msg = msg;
        this.ipData = ipData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ServerIpData getIpData() {
        return ipData;
    }

    public void setIpData(ServerIpData ipData) {
        this.ipData = ipData;
    }

    public static ServerIpData parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        ServerIpData dataServerIp = gson.fromJson(responce,ServerIpData.class);
        return dataServerIp;
    }
}
