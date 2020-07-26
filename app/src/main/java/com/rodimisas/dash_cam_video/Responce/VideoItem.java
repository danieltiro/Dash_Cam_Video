package com.rodimisas.dash_cam_video.Responce;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoItem implements Serializable {
    private String account;
    private String bindUserId;
    private int camera;
    private String createAt;
    private String deviceName;
    private int enabledFlag;
    private String eventType;
    @SerializedName("fileMd5")
    private String urlVideo;
    private String followId;
    private String fsize;
    private String id;
    private String imei;
    private String latlng;
    private String mcType;
    private String mimeType;
    private String qiliuIs;
    private String readStatus;
    private int shootTime;
    private int shootType;
    @SerializedName("thumbnail")
    private String miniatura;
    private String userId;

    public VideoItem() {
    }

    public VideoItem(String account, String bindUserId, int camera, String createAt, String deviceName, int enabledFlag, String eventType, String urlVideo, String followId, String fsize, String id, String imei, String latlng, String mcType, String mimeType, String qiliuIs, String readStatus, int shootTime, int shootType, String miniatura, String userId) {
        this.account = account;
        this.bindUserId = bindUserId;
        this.camera = camera;
        this.createAt = createAt;
        this.deviceName = deviceName;
        this.enabledFlag = enabledFlag;
        this.eventType = eventType;
        this.urlVideo = urlVideo;
        this.followId = followId;
        this.fsize = fsize;
        this.id = id;
        this.imei = imei;
        this.latlng = latlng;
        this.mcType = mcType;
        this.mimeType = mimeType;
        this.qiliuIs = qiliuIs;
        this.readStatus = readStatus;
        this.shootTime = shootTime;
        this.shootType = shootType;
        this.miniatura = miniatura;
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(String bindUserId) {
        this.bindUserId = bindUserId;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(int enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getQiliuIs() {
        return qiliuIs;
    }

    public void setQiliuIs(String qiliuIs) {
        this.qiliuIs = qiliuIs;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public int getShootTime() {
        return shootTime;
    }

    public void setShootTime(int shootTime) {
        this.shootTime = shootTime;
    }

    public int getShootType() {
        return shootType;
    }

    public void setShootType(int shootType) {
        this.shootType = shootType;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
