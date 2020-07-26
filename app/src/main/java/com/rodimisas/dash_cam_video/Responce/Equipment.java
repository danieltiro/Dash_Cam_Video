package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Equipment implements Serializable {
    private int code;
    @SerializedName("data")
    private List<EquipmentItem> equipos;
    private boolean ok;

    public Equipment() {
    }

    public Equipment(int code, List<EquipmentItem> equipos, boolean ok) {
        this.code = code;
        this.equipos = equipos;
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<EquipmentItem> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<EquipmentItem> equipos) {
        this.equipos = equipos;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static EquipmentItem parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        EquipmentItem data = gson.fromJson(responce,EquipmentItem.class);
        return data;
    }
}
