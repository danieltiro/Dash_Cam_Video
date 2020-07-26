package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class DataUserGroup implements Serializable {

    private String creationDate;
    private int enabledFlag;
    private String id;
    private String organizeName;
    private int total;
    private String type;
    private String userId;

    public DataUserGroup() {
    }

    public DataUserGroup(String creationDate, int enabledFlag, String id, String organizeName, int total, String type, String userId) {
        this.creationDate = creationDate;
        this.enabledFlag = enabledFlag;
        this.id = id;
        this.organizeName = organizeName;
        this.total = total;
        this.type = type;
        this.userId = userId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(int enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}