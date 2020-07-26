package com.rodimisas.dash_cam_video.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.rodimisas.dash_cam_video.Activities.DetailDeviceActivity;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapterIP;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.ServerIP;
import com.rodimisas.dash_cam_video.Responce.UrlVideo;
import com.rodimisas.dash_cam_video.utils.Util;

import java.util.Date;
import java.util.logging.StreamHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoFragment extends Fragment {
    private View rootView;
    private EquipmentItem equipo;
    private String cookie;
    private SharedPreferences prefs;
    private ServerIP ip;
    private long time;
    private String strTime;
    private UrlVideo urlVideo;
    private String URL_VIDEO;
    private SimpleExoPlayer player;
    private ImageView fullscreenButton;
    boolean fullscreen = false;

    public VideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_video, container, false);
        equipo = (EquipmentItem) getArguments().getSerializable("equipo");
        prefs = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        cookie = Util.getCookiePrefs(prefs);
        ((DetailDeviceActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getServerIp();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
    }

    private void getServerIp (){
        Call<ServerIP> ipCall = ApiAdapterIP.getApiService().getServerIp("2","getServerIp");
        ipCall.enqueue(new Callback<ServerIP>() {
            @Override
            public void onResponse(Call<ServerIP> call, Response<ServerIP> response) {
                ip = response.body();
                time = new Date().getTime();
                strTime = Long.toString(time);
                getURL(ip.getIpData().getServerIp(),equipo.getImei());
            }
            @Override
            public void onFailure(Call<ServerIP> call, Throwable t) {
                Toast.makeText(getContext(),"Error ServerIp",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getURL(String ip, String imei){
        Call<UrlVideo> urlCall = ApiAdapter.getApiService().getURL(cookie,
                "256",ip,imei,"3","1",strTime);
        urlCall.enqueue(new Callback<UrlVideo>() {
            @Override
            public void onResponse(Call<UrlVideo> call, Response<UrlVideo> response) {
                urlVideo = response.body();
                URL_VIDEO = urlVideo.getVideoData().getPullURL()+urlVideo.getVideoData().getToken();


                //initiate Player
                //Create a default TrackSelector
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                //Create the player
                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                final PlayerView playerView = getView().findViewById(R.id.simple_player);
                playerView.setPlayer(player);

                RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource.Factory(rtmpDataSourceFactory)
                        .createMediaSource(Uri.parse(URL_VIDEO));

                fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);
                fullscreenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(fullscreen) {
                            fullscreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.exo_controls_fullscreen_enter));
                            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                            if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                                ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                            }
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = (int) ( 200 * getContext().getResources().getDisplayMetrics().density);
                            playerView.setLayoutParams(params);
                            fullscreen = false;
                        }else{
                            fullscreenButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.exo_controls_fullscreen_exit));
                            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                            if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
                                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                            }
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                            params.width = params.MATCH_PARENT;
                            params.height = params.MATCH_PARENT;
                            playerView.setLayoutParams(params);
                            fullscreen = true;
                        }
                    }
                });


                // Prepare the player with the source.
                player.prepare(videoSource);
                //auto start playing
                player.setPlayWhenReady(true);
            }

            @Override
            public void onFailure(Call<UrlVideo> call, Throwable t) {
                Toast.makeText(getContext(),"Error URL_Video",Toast.LENGTH_LONG).show();
            }
        });
    }
}