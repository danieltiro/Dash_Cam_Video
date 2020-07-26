package com.rodimisas.dash_cam_video.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Adapters.DeviceAdapter;
import com.rodimisas.dash_cam_video.Fragments.MapDevicesFragment;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.DataUserGroup;
import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String cookie;
    private Equipment equipo;
    private List<Equipment> equipos = new ArrayList<>();
    private Fragment mapFragmen;
    private List<UserGroup> userGroup = new ArrayList<>();
    private List<DataUserGroup> dataUserGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);

        //tomar datos del intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userGroup = (List<UserGroup>) bundle.getSerializable("userGroup");
        } else{
        }

        for (int i=0; i<userGroup.size(); i++){
            dataUserGroup = userGroup.get(i).getGrupos();
            for (int j=0 ; j<dataUserGroup.size(); j++)
                getEquipment(dataUserGroup.get(j));
        }


    }

    private void getEquipment(DataUserGroup userGroup){
        Call<Equipment> equipmentCall = ApiAdapter.getApiService().getEquipment(cookie,
                userGroup.getUserId(),
                userGroup.getId(),
                "NORMAL",
                "8",
                "1",
                "0");
        equipmentCall.enqueue(new Callback<Equipment>() {
            @Override
            public void onResponse(Call<Equipment> call, Response<Equipment> response) {
                equipo = response.body();
                equipos.add(equipo);

                Bundle args = new Bundle();
                args.putSerializable("equipos", (Serializable) equipos);

                mapFragmen = new MapDevicesFragment();
                mapFragmen.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, mapFragmen).commit();
            }

            @Override
            public void onFailure(Call<Equipment> call, Throwable t) {
                Toast.makeText(MapsActivity.this,"Error equipmentList",Toast.LENGTH_LONG).show();
            }
        });
    }
}