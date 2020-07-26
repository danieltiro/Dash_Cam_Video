package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UrlVideoData implements Serializable {
    @SerializedName("ws-flv")
    private String wsFlv;
    private String pullURL;
    private String token;

    public UrlVideoData() {
    }

    public UrlVideoData(String wsFlv, String pullURL, String token) {
        this.wsFlv = wsFlv;
        this.pullURL = pullURL;
        this.token = token;
    }

    public String getWsFlv() {
        return wsFlv;
    }

    public void setWsFlv(String wsFlv) {
        this.wsFlv = wsFlv;
    }

    public String getPullURL() {
        return pullURL;
    }

    public void setPullURL(String pullURL) {
        this.pullURL = pullURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
