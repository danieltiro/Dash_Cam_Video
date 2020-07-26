package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Responce.DataOrgUserTree;
import com.rodimisas.dash_cam_video.Responce.DataUserGroup;
import com.rodimisas.dash_cam_video.Responce.DeviceCount;
import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.OrgUserTree;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.utils.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    ImageButton dispositivos;
    ImageButton ubicacion;
    ImageButton video;
    ImageButton listVideo;
    private SharedPreferences prefs;
    private OrgUserTree userTree;
    private List<DataOrgUserTree> users;
    private List<UserGroup> userGroup = new ArrayList<>();
    private List<DataUserGroup> dataUserGroup;
    private UserGroup group;
    private String cookie;
    private Equipment equipo;
    private List<Equipment> equipos =new ArrayList<>();
    private List<EquipmentItem> equiposItems = new ArrayList<>();
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);
        getUserTree();

        progressDialog = new ProgressDialog(MenuActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando...");

        equiposItems.add(new EquipmentItem("","","","",""));
        dispositivos = (ImageButton)findViewById(R.id.dispButton);
        ubicacion = (ImageButton)findViewById(R.id.mapButton);
        video = (ImageButton)findViewById(R.id.videoButton);
        listVideo = (ImageButton)findViewById(R.id.videosButton);

        dispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                Intent intent = new Intent(MenuActivity.this, DeviceActivity.class);
                intent.putExtra("userGroup",(Serializable) userGroup);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("userGroup",(Serializable) userGroup);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                for (int i=0; i<userGroup.size(); i++){
                    dataUserGroup = userGroup.get(i).getGrupos();
                    for (int j=0 ; j<dataUserGroup.size(); j++)
                        getEquipment(dataUserGroup.get(j));
                }

            }
        });
        listVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, VideoListActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_forget_logout:
                Util.removeSharedPreferences(prefs);
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void getUserTree(){
        Call<OrgUserTree> userTreeCall = ApiAdapter.getApiService().getUserTree(cookie);
        userTreeCall.enqueue(new Callback<OrgUserTree>() {
            @Override
            public void onResponse(Call<OrgUserTree> call, Response<OrgUserTree> response) {
                userTree = response.body();
                users = userTree.getData();
                for(int i=0; i<users.size();i++)
                {
                    getUserGroup(users.get(i).getDeviceCount());
                }
            }
            @Override
            public void onFailure(Call<OrgUserTree> call, Throwable t) {
                Toast.makeText(MenuActivity.this,"Error OrgUserTree",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserGroup(DeviceCount deviceCount){
        Call<UserGroup> userGroupCall = ApiAdapter.getApiService().getUserGroup(cookie,"NORMAL",
                deviceCount.getUserId(),
                "8",
                "1",
                "");
        userGroupCall.enqueue(new Callback<UserGroup>() {
            @Override
            public void onResponse(Call<UserGroup> call, Response<UserGroup> response) {
                group = response.body();
                userGroup.add(group);

            }
            @Override
            public void onFailure(Call<UserGroup> call, Throwable t) {
                Toast.makeText(MenuActivity.this,"Error GetUserGroup",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEquipment(final DataUserGroup dataUserGroup){
        Call<Equipment> equipmentCall = ApiAdapter.getApiService().getEquipment(cookie,
                dataUserGroup.getUserId(),
                dataUserGroup.getId(),
                "NORMAL",
                "8",
                "1",
                "0");
        equipmentCall.enqueue(new Callback<Equipment>() {
            @Override
            public void onResponse(Call<Equipment> call, Response<Equipment> response) {
                equipo = response.body();
                equipos.add(equipo);
                for(int i=0; i<equipos.size();i++){
                    for (int j=0 ; j<equipos.get(i).getEquipos().size();j++){
                        equiposItems.add(equipos.get(i).getEquipos().get(j));
                    }
                }
                Intent intent = new Intent(MenuActivity.this, VideoActivity.class);
                intent.putExtra("equipos",(Serializable) equiposItems);
                intent.putExtra("userGroup",(Serializable) userGroup);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Equipment> call, Throwable t) {
                Toast.makeText(MenuActivity.this,"Error equipmentList",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.dismiss();
    }
}