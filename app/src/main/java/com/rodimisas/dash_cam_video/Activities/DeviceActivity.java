

package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Adapters.DeviceAdapter;
import com.rodimisas.dash_cam_video.Objects.CatCamaras;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.DataUserGroup;
import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private DeviceAdapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private SharedPreferences prefs;
    private  List<CatCamaras> fotos;
    private String cookie;
    private Equipment equipo;
    private List<UserGroup> userGroup = new ArrayList<>();
    private List<DataUserGroup> dataUserGroup;
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);

        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tomar datos del intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userGroup = (List<UserGroup>) bundle.getSerializable("userGroup");

        } else{
        }

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        for (int i=0; i<userGroup.size(); i++){
            dataUserGroup = userGroup.get(i).getGrupos();
            for (int j=0 ; j<dataUserGroup.size(); j++)
            getEquipment(dataUserGroup.get(j));
        }
        progressDialog = new ProgressDialog(DeviceActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando...");
        //progressDialog.show();
        progressDialog.dismiss();
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
                recycler = (RecyclerView) findViewById(R.id.recyclerView);
                recycler.setHasFixedSize(true);
                recycler.setItemAnimator(new DefaultItemAnimator());
                myLayoutManager = new LinearLayoutManager(DeviceActivity.this);
                recycler.setLayoutManager(myLayoutManager);
                adapter = new DeviceAdapter(equipo.getEquipos(), R.layout.recycler_view_item, new DeviceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(EquipmentItem equipo, int position) {
                        Intent intent = new Intent(DeviceActivity.this, DetailDeviceActivity.class);
                        intent.putExtra("device", equipo);
                        startActivity(intent);
                    }
                });
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Equipment> call, Throwable t) {
                Toast.makeText(DeviceActivity.this,"Error equipmentList",Toast.LENGTH_LONG).show();
            }
        });
    }
}
