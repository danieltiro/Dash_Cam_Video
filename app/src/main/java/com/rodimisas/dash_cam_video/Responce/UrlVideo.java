package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UrlVideo implements Serializable {
    private String code;
    private Boolean ok;
    @SerializedName("data")
    private UrlVideoData videoData;

    public UrlVideo() {
    }

    public UrlVideo(String code, Boolean ok, UrlVideoData videoData) {
        this.code = code;
        this.ok = ok;
        this.videoData = videoData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public UrlVideoData getVideoData() {
        return videoData;
    }

    public void setVideoData(UrlVideoData videoData) {
        this.videoData = videoData;
    }

    public static UrlVideoData parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        UrlVideoData dataURL = gson.fromJson(responce,UrlVideoData.class);
        return dataURL;
    }
}
