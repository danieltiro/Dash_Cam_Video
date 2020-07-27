package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class ContentTalk implements Serializable {

    private Talk talk;

    public Talk getTalk() {
        return talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public static Talk parseJSON(String response){
        Gson gson = new GsonBuilder().setLenient().create();
        Talk talk = null;
        if(response != null) {
            talk = gson.fromJson(response, Talk.class);
        }
        return talk;
    }
}
