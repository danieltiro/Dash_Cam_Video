package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UrlTalk implements Serializable {
    private String code;
    @SerializedName("data")
    private DataTalk dataTalk;
    private String msg;

    public UrlTalk() {
    }

    public UrlTalk(String code, DataTalk dataTalk, String msg) {
        this.code = code;
        this.dataTalk = dataTalk;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataTalk getDataTalk() {
        return dataTalk;
    }

    public void setDataTalk(DataTalk dataTalk) {
        this.dataTalk = dataTalk;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static DataTalk parseJSON(String response){
        Gson gson = new GsonBuilder().setLenient().create();
        DataTalk data = null;
        if(response != null) {
            data =gson.fromJson(response, DataTalk.class);
        }
        return data;
    }
}
