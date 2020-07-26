package com.rodimisas.dash_cam_video.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Interfaces.DashCamService;
import com.rodimisas.dash_cam_video.Responce.LoginPermiso;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.utils.Util;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button btnLogin;
    private LoginPermiso login;
    private boolean result;
    private String name = "";
    private String cookie;
    ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editTextUsuario.getText().toString();
                String password = editTextPassword.getText().toString();
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();
                login(user, password);
            }
        });
    }

    private void bindUI(){
        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
    }

    private void setCredentialsIfExist(){
        String user = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            editTextUsuario.setText(user);
            editTextPassword.setText(password);
            switchRemember.setChecked(true);
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
                progressDialog.dismiss();
                if (login != null && login.getCode() == 0){
                    //Toast.makeText(LoginActivity.this,login.getMsg(),Toast.LENGTH_LONG).show();
                    goToMenu();
                    saveOnPreferences(user, password);
                } else {
                    Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginPermiso> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveOnPreferences (String user, String password){
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", user);
            editor.putString("pass", password);
            editor.putString("cookie",cookie);
            editor.apply();
        }
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
