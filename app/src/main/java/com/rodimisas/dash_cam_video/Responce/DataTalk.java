package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DataTalk implements Serializable {

    @SerializedName("content")
    private String contentTalk;

    public String getContentTalk() {
        return contentTalk;
    }

    public void setContentTalk(String contentTalk) {
        this.contentTalk = contentTalk;
    }

    /*
    public static ContentTalk parseJSON(String response){
        Gson gson = new GsonBuilder().setLenient().create();
        ContentTalk content = gson.fromJson(response, ContentTalk.class);
        return content;
    }
    */
}
