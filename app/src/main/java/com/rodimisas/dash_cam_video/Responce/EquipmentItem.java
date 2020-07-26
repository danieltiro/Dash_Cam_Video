package com.rodimisas.dash_cam_video.Responce;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EquipmentItem implements Serializable {
    private String GPSSignal;
    private String otherPosTime;
    private String gpsTime;
    private String gpsNum;
    private String direction;
    private String statusStr;
    private String mcType;
    private String statusAbstract;
    @SerializedName("lng")
    private EquipmentItemRuntime longitud;
    private String orgId;
    private int distance;
    private String deviceName;
    private String electricFenceFlag;
    private String dmsFlag;
    private String activationTime;
    private String mcTypeAlias;
    private String userId;
    private String positionType;
    @SerializedName("hasFJc400sFlag")
    private String fJFlag;
    private String followFlag;
    private String driverName;
    @SerializedName("lat")
    private EquipmentItemRuntime latitud;
    private String showTemperature;
    private String icon;
    private String showVoltage;
    private String status;
    private String expiration;
    private String imei;
    private String acc;
    private String accFlag;
    private String account;
    private String hbTime;
    private String speed;

    public EquipmentItem() {
    }

    public EquipmentItem(String mcType, String orgId, String deviceName, String userId, String imei) {
        this.mcType = mcType;
        this.orgId = orgId;
        this.deviceName = deviceName;
        this.userId = userId;
        this.imei = imei;
    }

    public EquipmentItem(String deviceName) {
        this.deviceName = deviceName;
    }

    public EquipmentItem(String GPSSignal, String otherPosTime, String gpsTime, String gpsNum, String direction, String statusStr, String mcType, String statusAbstract, EquipmentItemRuntime longitud, String orgId, int distance, String deviceName, String electricFenceFlag, String dmsFlag, String activationTime, String mcTypeAlias, String userId, String positionType, String fJFlag, String followFlag, String driverName, EquipmentItemRuntime latitud, String showTemperature, String icon, String showVoltage, String status, String expiration, String imei, String acc, String accFlag, String account, String hbTime) {
        this.GPSSignal = GPSSignal;
        this.otherPosTime = otherPosTime;
        this.gpsTime = gpsTime;
        this.gpsNum = gpsNum;
        this.direction = direction;
        this.statusStr = statusStr;
        this.mcType = mcType;
        this.statusAbstract = statusAbstract;
        this.longitud = longitud;
        this.orgId = orgId;
        this.distance = distance;
        this.deviceName = deviceName;
        this.electricFenceFlag = electricFenceFlag;
        this.dmsFlag = dmsFlag;
        this.activationTime = activationTime;
        this.mcTypeAlias = mcTypeAlias;
        this.userId = userId;
        this.positionType = positionType;
        this.fJFlag = fJFlag;
        this.followFlag = followFlag;
        this.driverName = driverName;
        this.latitud = latitud;
        this.showTemperature = showTemperature;
        this.icon = icon;
        this.showVoltage = showVoltage;
        this.status = status;
        this.expiration = expiration;
        this.imei = imei;
        this.acc = acc;
        this.accFlag = accFlag;
        this.account = account;
        this.hbTime = hbTime;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getGPSSignal() {
        return GPSSignal;
    }

    public void setGPSSignal(String GPSSignal) {
        this.GPSSignal = GPSSignal;
    }

    public String getOtherPosTime() {
        return otherPosTime;
    }

    public void setOtherPosTime(String otherPosTime) {
        this.otherPosTime = otherPosTime;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getGpsNum() {
        return gpsNum;
    }

    public void setGpsNum(String gpsNum) {
        this.gpsNum = gpsNum;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getStatusAbstract() {
        return statusAbstract;
    }

    public void setStatusAbstract(String statusAbstract) {
        this.statusAbstract = statusAbstract;
    }

    public EquipmentItemRuntime getLongitud() {
        return longitud;
    }

    public void setLongitud(EquipmentItemRuntime longitud) {
        this.longitud = longitud;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getElectricFenceFlag() {
        return electricFenceFlag;
    }

    public void setElectricFenceFlag(String electricFenceFlag) {
        this.electricFenceFlag = electricFenceFlag;
    }

    public String getDmsFlag() {
        return dmsFlag;
    }

    public void setDmsFlag(String dmsFlag) {
        this.dmsFlag = dmsFlag;
    }

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public String getMcTypeAlias() {
        return mcTypeAlias;
    }

    public void setMcTypeAlias(String mcTypeAlias) {
        this.mcTypeAlias = mcTypeAlias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getfJFlag() {
        return fJFlag;
    }

    public void setfJFlag(String fJFlag) {
        this.fJFlag = fJFlag;
    }

    public String getFollowFlag() {
        return followFlag;
    }

    public void setFollowFlag(String followFlag) {
        this.followFlag = followFlag;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public EquipmentItemRuntime getLatitud() {
        return latitud;
    }

    public void setLatitud(EquipmentItemRuntime latitud) {
        this.latitud = latitud;
    }

    public String getShowTemperature() {
        return showTemperature;
    }

    public void setShowTemperature(String showTemperature) {
        this.showTemperature = showTemperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShowVoltage() {
        return showVoltage;
    }

    public void setShowVoltage(String showVoltage) {
        this.showVoltage = showVoltage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getAccFlag() {
        return accFlag;
    }

    public void setAccFlag(String accFlag) {
        this.accFlag = accFlag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHbTime() {
        return hbTime;
    }

    public void setHbTime(String hbTime) {
        this.hbTime = hbTime;
    }

    @Override
    public String toString() {
        return deviceName;
    }
}