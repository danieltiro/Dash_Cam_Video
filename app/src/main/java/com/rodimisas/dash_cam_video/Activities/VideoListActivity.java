package com.rodimisas.dash_cam_video.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.utils.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VideoListActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String cookie;
    private String video_link;
    private VideoView videoView;
    private ProgressDialog pd;
    private MediaController ctlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);
        // Activar la flecha para volver al activity principal
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd = new ProgressDialog(VideoListActivity.this);
        pd.setMessage("Cargando Video...");
        pd.show();
        video_link = "http://static2.jimicloud.com/357730090901217_17163348_2020_07_18_10_27_49_01";
        videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoPath(video_link);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ctlr = new MediaController(VideoListActivity.this);
                ctlr.setMediaPlayer(videoView);
                videoView.setMediaController(ctlr);
                videoView.requestFocus();
                pd.dismiss();
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
}