package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerIpData implements Serializable {
    private String serverIp;

    public ServerIpData() {
    }

    public ServerIpData(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}
