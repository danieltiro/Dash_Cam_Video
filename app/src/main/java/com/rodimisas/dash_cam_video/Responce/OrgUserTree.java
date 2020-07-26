package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class OrgUserTree {
    public int code;
    @SerializedName("data")
    public List<DataOrgUserTree> datas;
    public Boolean ok;

    public OrgUserTree() {
    }

    public OrgUserTree(int code, Boolean ok) {
        code = code;
        this.datas = datas;
        this.ok = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        code = code;
    }

    public List<DataOrgUserTree> getData() {
        return datas;
    }

    public void setData(List<DataOrgUserTree> datas) {
        this.datas = datas;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public static DataOrgUserTree parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        DataOrgUserTree data = gson.fromJson(responce,DataOrgUserTree.class);
        return data;
    }
}
