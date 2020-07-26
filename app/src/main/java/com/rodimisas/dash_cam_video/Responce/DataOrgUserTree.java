package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class DataOrgUserTree implements Serializable {
    public String account;
    public String appUpdateDevFlag;
    public Boolean checked;
    public Boolean chkDisabled;
    public DeviceCount deviceCount;
    public String fullParentId;
    public String iconSkin;
    public String id;
    public String isBatchSendFM;
    public String isBatchSendIns;
    public Boolean isParent;
    public String name;
    public String nickName;
    public Boolean open;
    public int type;
    public String updateDevFlag;

    public DataOrgUserTree() {
    }

    public String getAccount() {
        return account;
    }

    public DataOrgUserTree(String account, String appUpdateDevFlag,
                           Boolean checked, Boolean chkDisabled,
                           DeviceCount deviceCount, String fullParentId,
                           String iconSkin, String id, String isBatchSendFM,
                           String isBatchSendIns, Boolean isParent,
                           String name, String nickName, Boolean open,
                           int type, String updateDevFlag) {
        this.account = account;
        this.appUpdateDevFlag = appUpdateDevFlag;
        this.checked = checked;
        this.chkDisabled = chkDisabled;
        this.deviceCount = deviceCount;
        this.fullParentId = fullParentId;
        this.iconSkin = iconSkin;
        this.id = id;
        this.isBatchSendFM = isBatchSendFM;
        this.isBatchSendIns = isBatchSendIns;
        this.isParent = isParent;
        this.name = name;
        this.nickName = nickName;
        this.open = open;
        this.type = type;
        this.updateDevFlag = updateDevFlag;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAppUpdateDevFlag() {
        return appUpdateDevFlag;
    }

    public void setAppUpdateDevFlag(String appUpdateDevFlag) {
        this.appUpdateDevFlag = appUpdateDevFlag;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public DeviceCount getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(DeviceCount deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getFullParentId() {
        return fullParentId;
    }

    public void setFullParentId(String fullParentId) {
        this.fullParentId = fullParentId;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsBatchSendFM() {
        return isBatchSendFM;
    }

    public void setIsBatchSendFM(String isBatchSendFM) {
        this.isBatchSendFM = isBatchSendFM;
    }

    public String getIsBatchSendIns() {
        return isBatchSendIns;
    }

    public void setIsBatchSendIns(String isBatchSendIns) {
        this.isBatchSendIns = isBatchSendIns;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateDevFlag() {
        return updateDevFlag;
    }

    public void setUpdateDevFlag(String updateDevFlag) {
        this.updateDevFlag = updateDevFlag;
    }

    public static DeviceCount parseJSON(String responce){
        Gson gson = new GsonBuilder().setLenient().create();
        DeviceCount deviceCount = gson.fromJson(responce,DeviceCount.class);
        return deviceCount;
    }
}