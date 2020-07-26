package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.rodimisas.dash_cam_video.Responce.DataUserGroup;

import java.io.Serializable;
import java.util.List;

public class UserGroup implements Serializable {
    private int code;
    @SerializedName("data")
    private List<DataUserGroup> grupos;
    private boolean ok;

    public UserGroup() {
    }

    public UserGroup(int code, List<DataUserGroup> grupos, boolean ok) {
        this.code = code;
        this.grupos = grupos;
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataUserGroup> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<DataUserGroup> grupos) {
        this.grupos = grupos;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static DataUserGroup parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        DataUserGroup data = gson.fromJson(responce,DataUserGroup.class);
       return data;
    }
}
