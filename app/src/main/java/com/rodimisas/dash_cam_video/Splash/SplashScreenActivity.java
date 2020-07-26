package com.rodimisas.dash_cam_video.Splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rodimisas.dash_cam_video.Activities.LoginActivity;
import com.rodimisas.dash_cam_video.Activities.MenuActivity;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.LoginPermiso;
import com.rodimisas.dash_cam_video.utils.Util;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private LoginPermiso login;
    private Intent intentMenu;
    private String cookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        if (!TextUtils.isEmpty(Util.getUserMailPrefs(prefs)) &&
                !TextUtils.isEmpty(Util.getUserPassPrefs(prefs))) {
            login(Util.getUserMailPrefs(prefs),Util.getUserPassPrefs(prefs));
        } else {
            goToLogin();
        }
    }

    private void login (final String user, final String password){
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        Call<LoginPermiso> loginCall = ApiAdapter.getApiService().validaLogin("1",
                "login",
                user,
                "98|111|110|97|102|111|110|116|50|48|50|48",
                //"119|101|98|102|108|101|101|116|50|48|49|57",
                "en",
                "");
        loginCall.enqueue(new Callback<LoginPermiso>() {
            @Override
            public void onResponse(Call<LoginPermiso> call, Response<LoginPermiso> response) {
                login = response.body();
                cookie = response.headers().get("Set-Cookie");

                if (login != null && login.getCode() == 0){
                    //Toast.makeText(SplashScreenActivity.this,login.getMsg(),Toast.LENGTH_LONG).show();
                    goToMenu();
                    saveOnPreferences(user,password);
                } else {
                    goToLogin();
                    Toast.makeText(SplashScreenActivity.this,"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginPermiso> call, Throwable t) {
                goToLogin();
                Toast.makeText(SplashScreenActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void saveOnPreferences (String user, String password){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", user);
            editor.putString("pass", password);
            editor.putString("cookie",cookie);
            editor.apply();
    }
}
