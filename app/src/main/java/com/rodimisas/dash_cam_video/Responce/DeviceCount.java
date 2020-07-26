package com.rodimisas.dash_cam_video.Responce;

import androidx.annotation.Nullable;

public class DeviceCount {
   public int aboutToExpire;
   public int active;
   public int enabledFlag;
   public int expired;
   public int noOnlineNum;
   public int noactive;
   @Nullable
   public String numStr;
   public int onLineNum;
   public int perActive;
   public int perNoActive;
   public int repertory;
   public int stock;
   public String userId;
   public int userNum;

   public DeviceCount() {
   }

   public DeviceCount(int aboutToExpire,
                      int active, int enabledFlag,
                      int expired, int noOnlineNum,
                      int noactive, String numStr,
                      int onLineNum, int perActive,
                      int perNoActive, int repertory,
                      String userId, int stock, int userNum) {
      this.aboutToExpire = aboutToExpire;
      this.active = active;
      this.enabledFlag = enabledFlag;
      this.expired = expired;
      this.noOnlineNum = noOnlineNum;
      this.noactive = noactive;
      this.numStr = numStr;
      this.onLineNum = onLineNum;
      this.perActive = perActive;
      this.perNoActive = perNoActive;
      this.repertory = repertory;
      this.userId = userId;
      this.stock = stock;
      this.userNum = userNum;
   }

   public int getAboutToExpire() {
      return aboutToExpire;
   }

   public void setAboutToExpire(int aboutToExpire) {
      this.aboutToExpire = aboutToExpire;
   }

   public int getActive() {
      return active;
   }

   public void setActive(int active) {
      this.active = active;
   }

   public int getEnabledFlag() {
      return enabledFlag;
   }

   public void setEnabledFlag(int enabledFlag) {
      this.enabledFlag = enabledFlag;
   }

   public int getExpired() {
      return expired;
   }

   public void setExpired(int expired) {
      this.expired = expired;
   }

   public int getNoOnlineNum() {
      return noOnlineNum;
   }

   public void setNoOnlineNum(int noOnlineNum) {
      this.noOnlineNum = noOnlineNum;
   }

   public int getNoactive() {
      return noactive;
   }

   public void setNoactive(int noactive) {
      this.noactive = noactive;
   }

   public String getNumStr() {
      return numStr;
   }

   public void setNumStr(String numStr) {
      this.numStr = numStr;
   }

   public int getOnLineNum() {
      return onLineNum;
   }

   public void setOnLineNum(int onLineNum) {
      this.onLineNum = onLineNum;
   }

   public int getPerActive() {
      return perActive;
   }

   public void setPerActive(int perActive) {
      this.perActive = perActive;
   }

   public int getPerNoActive() {
      return perNoActive;
   }

   public void setPerNoActive(int perNoActive) {
      this.perNoActive = perNoActive;
   }

   public int getRepertory() {
      return repertory;
   }

   public void setRepertory(int repertory) {
      this.repertory = repertory;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public int getStock() {
      return stock;
   }

   public void setStock(int stock) {
      this.stock = stock;
   }

   public int getUserNum() {
      return userNum;
   }

   public void setUserNum(int userNum) {
      this.userNum = userNum;
   }
}
