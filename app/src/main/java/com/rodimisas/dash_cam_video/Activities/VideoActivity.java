package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Adapters.DeviceAdapter;
import com.rodimisas.dash_cam_video.Fragments.DatePickerFragment;
import com.rodimisas.dash_cam_video.Objects.CatCamaras;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.DataUserGroup;
import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.OrgUserTree;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.Responce.VideoList;
import com.rodimisas.dash_cam_video.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences prefs;
    private String cookie;
    private long time;
    EditText fechaIni;
    EditText fechaFin;
    private List<EquipmentItem> equipos = new ArrayList<>();
    private List<UserGroup> userGroup = new ArrayList<>();
    private List<DataUserGroup> dataUserGroup;
    private List<CatCamaras> camspinner;
    private EquipmentItem equipo;
    private VideoList recordVideos;
    private Spinner spinnerEquipos;
    private Spinner spinnerCamara;
    private Button buscar;
    private CatCamaras camara;
    private String userId;
    private String orgId;
    private String startDate;
    private String endDate;
    ProgressDialog progressDialog = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        spinnerEquipos = (Spinner) findViewById(R.id.spinnerDeviceName);
        spinnerCamara = (Spinner) findViewById(R.id.spinnerCamara);
        fechaIni = (EditText) findViewById(R.id.fechaIni);
        fechaFin = (EditText) findViewById(R.id.fechaFin);
        buscar = (Button) findViewById(R.id.button);

        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        camspinner = Util.getCamspinner();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);

        //tomar datos del intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            equipos = (List<EquipmentItem>) bundle.getSerializable("equipos");
            userGroup = (List<UserGroup>) bundle.getSerializable("userGroup");
        } else{
        }

        for (int i=0; i<userGroup.size(); i++){
            dataUserGroup = userGroup.get(i).getGrupos();
            for (int j=0 ; j<dataUserGroup.size(); j++){
                userId=dataUserGroup.get(j).getUserId();
                orgId = dataUserGroup.get(j).getId();
            }
        }

        progressDialog = new ProgressDialog(VideoActivity.this);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Cargando...");
        //progressDialog.show();
        progressDialog.dismiss();
        ArrayAdapter<CatCamaras> adapterCamara = new ArrayAdapter<CatCamaras>(VideoActivity.this, android.R.layout.simple_spinner_dropdown_item, camspinner);
        ArrayAdapter<EquipmentItem> adapterEquipos = new ArrayAdapter<EquipmentItem>(VideoActivity.this, android.R.layout.simple_spinner_dropdown_item, equipos);
        spinnerEquipos.setAdapter(adapterEquipos);

        fechaIni.setOnClickListener(this);
        fechaFin.setOnClickListener(this);
        buscar.setOnClickListener(this);
        spinnerCamara.setAdapter(adapterCamara);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fechaIni:
                showDatePickerDialog(view);
                break;
            case R.id.fechaFin:
                showDatePickerDialog(view);
                break;
            case R.id.button:
                equipo = (EquipmentItem) spinnerEquipos.getSelectedItem();
                camara = (CatCamaras) spinnerCamara.getSelectedItem();
                getVideos();
                break;
        }
    }

    private void showDatePickerDialog(final View view) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                String strDay = String.format("%02d", day).trim();
                String strMonth = String.format("%02d", (month+1)).trim();
                String strYear = String.valueOf(year).trim();
                switch (view.getId()) {
                    case R.id.fechaIni:
                        fechaIni.setText(selectedDate);
                        startDate = strYear + "_" + strMonth + "_" + strDay;
                        break;
                    case R.id.fechaFin:
                        fechaFin.setText(selectedDate);
                        endDate = strYear + "_" + strMonth + "_" + strDay;
                        break;
                }
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
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

    private void getVideos() {
        Call<VideoList> videosCall = ApiAdapter.getApiService().getVideos(cookie,
                equipo.getDeviceName(),
                startDate,
                endDate,
                "",
                "0",
                equipo.getMcType(),
                orgId,
                "1",
                camara.getValor(),
                "10",
                "",
                equipo.getImei(),
                userId);
        videosCall.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                recordVideos = response.body();
                Intent intent = new Intent(VideoActivity.this, RecordsActivity.class);
                intent.putExtra("grabaciones",(Serializable) recordVideos);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {

            }
        });
    }
}