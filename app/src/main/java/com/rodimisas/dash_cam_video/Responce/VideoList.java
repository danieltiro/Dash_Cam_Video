package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoList implements Serializable {
    private String code;
    @SerializedName("data")
    private DataVideoList dataVideo;
    private String ok;

    public VideoList() {
    }

    public VideoList(String code, DataVideoList dataVideo, String ok) {
        this.code = code;
        this.dataVideo = dataVideo;
        this.ok = ok;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataVideoList getDataVideo() {
        return dataVideo;
    }

    public void setDataVideo(DataVideoList dataVideo) {
        this.dataVideo = dataVideo;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
    public static DataVideoList parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        DataVideoList data = gson.fromJson(responce,DataVideoList.class);
        return data;
    }
}
