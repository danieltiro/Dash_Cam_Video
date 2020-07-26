package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rodimisas.dash_cam_video.Adapters.DeviceAdapter;
import com.rodimisas.dash_cam_video.Adapters.RecordAdapter;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.UserGroup;
import com.rodimisas.dash_cam_video.Responce.VideoItem;
import com.rodimisas.dash_cam_video.Responce.VideoList;
import com.rodimisas.dash_cam_video.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String cookie;
    private VideoList recordVideos;
    private List<VideoItem> records= new ArrayList<>();
    private RecyclerView recycler;
    private RecyclerView.LayoutManager myLayoutManager;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);

        //tomar datos del intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            recordVideos = (VideoList) bundle.getSerializable("grabaciones");
            records = recordVideos.getDataVideo().getVideos();
        } else{
        }

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        myLayoutManager = new LinearLayoutManager(RecordsActivity.this);
        recycler.setLayoutManager(myLayoutManager);

        adapter = new RecordAdapter(records, R.layout.records_item, new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoItem record, int position) {
                Intent intent = new Intent(RecordsActivity.this, RecordActivity.class);
                intent.putExtra("record", record);
                startActivity(intent);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                this.finish();
                return true;
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
}