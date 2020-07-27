package com.rodimisas.dash_cam_video.Responce;

import java.io.Serializable;

public class Talk implements Serializable {

    private String talkUrl;

    public String getTalkUrl() {
        return talkUrl;
    }

    public void setTalkUrl(String talkUrl) {
        this.talkUrl = talkUrl;
    }
}
