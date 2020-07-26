package com.rodimisas.dash_cam_video.Interfaces;

import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.LoginPermiso;
import com.rodimisas.dash_cam_video.Responce.OrgUserTree;
import com.rodimisas.dash_cam_video.Objects.CatCamaras;
import com.rodimisas.dash_cam_video.Responce.ServerIP;
import com.rodimisas.dash_cam_video.Responce.UrlVideo;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.Responce.VideoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DashCamService {
    //https://jsonplaceholder.typicode.com/photos
    @GET("photos")
        Call<List<CatCamaras>> getPhotos();

    @FormUrlEncoded
    @POST("api/regdc")
    Call<LoginPermiso> validaLogin(@Field("ver") String ver,
                                   @Field("method") String method,
                                   @Field("account") String account,
                                   @Field("password") String password,
                                   @Field("language") String language,
                                   @Field("validCode") String validCode);

    @GET("customer/getOrgUserTree")
        Call<OrgUserTree> getUserTree(@Header("Cookie") String cookie);

    @FormUrlEncoded
    @POST("console/getUserGroup")
    Call<UserGroup> getUserGroup(@Header("Cookie") String cookie,
                                 @Field("type") String type,
                                 @Field("userId") String userId,
                                 @Field("userType") String userType,
                                 @Field("isNewMcType") String mcType,
                                 @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("console/queryEquipmentList")
    Call<Equipment> getEquipment(@Header("Cookie") String cookie,
                                 @Field("userId") String userId,
                                 @Field("orgId") String orgId,
                                 @Field("type") String type,
                                 @Field("userType") String userType,
                                 @Field("isNewMcType") String mcType,
                                 @Field("startRow") String startRow);
    @FormUrlEncoded
    @POST("srs-os/jsonpApi")
    Call<ServerIP> getServerIp( @Field("ver") String ver,
                                @Field("method") String method);

    @FormUrlEncoded
    @POST("console/sendInstruction")
    Call<UrlVideo> getURL(@Header("Cookie") String cookie,
                                @Field("cmd") String cmd,
                                @Field("ip") String ip,
                                @Field("imei") String imei,
                                @Field("isw") String isw,
                                @Field("acc") String acc,
                                @Field("time") String time);

    @FormUrlEncoded
    @POST("video/getVideoList")
    Call<VideoList> getVideos(@Header("Cookie") String cookie,
                              @Field("deviceName") String deviceName,
                              @Field("startDate") String startDate,
                              @Field("endDate") String endDate,
                              @Field("eventType") String eventType,
                              @Field("lowerflag") String lowerflag,
                              @Field("mcType") String mcType,
                              @Field("orgId") String orgId,
                              @Field("pageNo") String pageNo,
                              @Field("camera") String camera,
                              @Field("pageSize") String pageSize,
                              @Field("readStatus") String readStatus,
                              @Field("imei") String imei,
                              @Field("userId") String userId);
}
