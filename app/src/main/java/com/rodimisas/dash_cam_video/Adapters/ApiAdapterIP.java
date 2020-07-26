package com.rodimisas.dash_cam_video.Adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodimisas.dash_cam_video.Interceptor.CookieInterceptor;
import com.rodimisas.dash_cam_video.Interfaces.DashCamService;
import com.rodimisas.dash_cam_video.Responce.EquipmentItemRuntime;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapterIP {
    private static DashCamService API_SERVICE;

    public static DashCamService getApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieHandler cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(logging)
                .addInterceptor(new CookieInterceptor()).build();
                //.connectTimeout(5, TimeUnit.SECONDS)
                //.writeTimeout(5, TimeUnit.SECONDS)
                //.callTimeout(5,TimeUnit.SECONDS)
                //.readTimeout(5, TimeUnit.SECONDS).build();
        String baseUrl = "http://live.jimivideo.com";

        if (API_SERVICE == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Integer.class,new IntTypeAdapter())
                    .registerTypeAdapter(Long.class, new LongTypeAdapter())
                    .registerTypeAdapter(EquipmentItemRuntime.class,new EquipmentItemRuntimeTypeAdapter())
                    .registerTypeAdapter(Float.class, new FloatTypeAdapter())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .callFactory(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            API_SERVICE = retrofit.create(DashCamService.class);
        }

        return API_SERVICE;
    }
}
