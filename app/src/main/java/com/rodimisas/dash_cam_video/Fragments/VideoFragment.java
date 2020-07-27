package com.rodimisas.dash_cam_video.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pedro.rtplibrary.rtmp.RtmpOnlyAudio;
import com.rodimisas.dash_cam_video.Activities.DetailDeviceActivity;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapter;
import com.rodimisas.dash_cam_video.Adapters.ApiAdapterIP;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.ServerIP;
import com.rodimisas.dash_cam_video.Responce.UrlTalk;
import com.rodimisas.dash_cam_video.Responce.UrlVideo;
import com.rodimisas.dash_cam_video.utils.STATUS_MICRO;
import com.rodimisas.dash_cam_video.utils.Util;

import net.ossrs.rtmp.ConnectCheckerRtmp;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.logging.StreamHandler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment implements View.OnTouchListener, ConnectCheckerRtmp {

    private static final String TAG = "VideoFragment";

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
    private Button mMicrophone;
    private TextView mStatusMicro;
    private RtmpOnlyAudio rtmpOnlyAudio;
    private Boolean isRecordAudio = false;

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
        mMicrophone = (Button)rootView.findViewById(R.id.b_microphone);
        mStatusMicro = (TextView)rootView.findViewById(R.id.status_microphone);
        mMicrophone.setOnTouchListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rtmpOnlyAudio = new RtmpOnlyAudio(this);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(view.getId()){
            case R.id.b_microphone:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    mMicrophone.setPressed(true);
                    startTalk(equipo.getImei());
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    mMicrophone.setPressed(false);
                    stopTalk(equipo.getImei());
                }
                break;
        }
        return true;
    }

    @Override
    public void onConnectionSuccessRtmp() {
        Log.d(TAG,"onConnectionSuccessRtmp");
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "onConnectionSuccessRtmp", Toast.LENGTH_SHORT).show();
                isRecordAudio = true;
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(@NonNull final String reason) {
        Log.e(TAG,"onConnectionFailedRtmp: " + reason);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                rtmpOnlyAudio.stopStream();
                stopTalk(equipo.getImei());
                Toast.makeText(getContext(), "onConnectionFailedRtmp: " + reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNewBitrateRtmp(long bitrate) {
        Log.d(TAG,"onNewBitrateRtmp");
    }

    @Override
    public void onDisconnectRtmp() {
        Log.d(TAG,"onDisconnectRtmp");
    }

    @Override
    public void onAuthErrorRtmp() {
        Log.d(TAG,"onAuthErrorRtmp");
    }

    @Override
    public void onAuthSuccessRtmp() {
        Log.d(TAG,"onAuthSuccessRtmp");
    }

    public void startTalk(String imei){
        if(!isRecordAudio){
            //Toast.makeText(getContext(), "Inicia grabación de audio", Toast.LENGTH_SHORT).show();
            updateStatusMicro(STATUS_MICRO.CONNECTING);
            //START TALK
            //http://live.jimivideo.com/srs-os/api?ver=2&method=sendInstruction&devKey=a15f493882ea4dbd96fe204a9f040471
            // &devSecret=126588f6de5e4066a1e6ebe792d9873d&uuid=357730090901480&proNo=128
            // &cmd={"appid":"A86947F92073A00","cmd":272}&token=123456&isw=3&appId=A86947F92073A00
            Call<UrlTalk> talkCall = ApiAdapterIP.getApiService().sendTalk(
                    "2",
                    "sendInstruction",
                    "a15f493882ea4dbd96fe204a9f040471",
                    "126588f6de5e4066a1e6ebe792d9873d",
                    imei,
                    "128",
                    "{\"appid\":\"A86947F92073A00\",\"cmd\":272}",
                    "123456",
                    "3",
                    "A86947F92073A00"
            );
            talkCall.enqueue(new Callback<UrlTalk>() {
                @Override
                public void onResponse(Call<UrlTalk> call, Response<UrlTalk> response) {
                    if(response.isSuccessful() && response.body() != null && response.body().getDataTalk() != null) {
                        try {
                            Log.d(TAG, response.body().toString());
                            JSONObject object = new JSONObject(response.body().getDataTalk().getContentTalk());
                            String urlTalk = object.get("talkUrl").toString();
                            if(rtmpOnlyAudio.prepareAudio()) {
                                rtmpOnlyAudio.startStream(urlTalk);
                                isRecordAudio = true;
                                updateStatusMicro(STATUS_MICRO.RECORDING);
                            }
                        } catch (JSONException e) {
                            stopTalk(equipo.getImei());
                            e.printStackTrace();
                            updateStatusMicro(STATUS_MICRO.NONE);
                        }
                    }
                }
                @Override
                public void onFailure(Call<UrlTalk> call, Throwable t) {
                    stopTalk(equipo.getImei());
                    //Toast.makeText(getContext(),"Error talkCall: " + t.getMessage(),Toast.LENGTH_LONG).show();
                    updateStatusMicro(STATUS_MICRO.NONE);
                }
            });
        }
    }
    public void stopTalk(String imei){
        if(isRecordAudio) {
            //Toast.makeText(getContext(), "Detener grabación de audio", Toast.LENGTH_SHORT).show();
            updateStatusMicro(STATUS_MICRO.NONE);
            rtmpOnlyAudio.stopStream();
            isRecordAudio = false;
            //STOP TALK
            //http://live.jimivideo.com/srs-os/api?ver=2&method=sendInstruction&devKey=a15f493882ea4dbd96fe204a9f040471
            // &devSecret=126588f6de5e4066a1e6ebe792d9873d&uuid=357730090901480&proNo=128
            // &cmd={"appid":"A86947F92073A00","cmd":273}&token=123456&isw=3&appId=A86947F92073A00
            Call<UrlTalk> talkCall = ApiAdapterIP.getApiService().sendTalk(
                    "2",
                    "sendInstruction",
                    "a15f493882ea4dbd96fe204a9f040471",
                    "126588f6de5e4066a1e6ebe792d9873d",
                    imei,
                    "128",
                    "{\"appid\":\"A86947F92073A00\",\"cmd\":273}",
                    "123456",
                    "3",
                    "A86947F92073A00"
            );
            talkCall.enqueue(new Callback<UrlTalk>() {
                @Override
                public void onResponse(Call<UrlTalk> call, Response<UrlTalk> response) {
                    if(response.isSuccessful()) {
                        Log.d(TAG, response.body().toString());
                    }
                }
                @Override
                public void onFailure(Call<UrlTalk> call, Throwable t) {
                    Toast.makeText(getContext(),"Error talkCall: " + t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void updateStatusMicro(STATUS_MICRO statusMicro){
        switch(statusMicro){
            case NONE:
                mStatusMicro.setVisibility(View.INVISIBLE);
                mStatusMicro.setText("");
                break;
            case RECORDING:
                mStatusMicro.setVisibility(View.VISIBLE);
                mStatusMicro.setBackgroundColor(Color.RED);
                mStatusMicro.setText(R.string.status_micro_recording);
                break;
            case CONNECTING:
                mStatusMicro.setVisibility(View.VISIBLE);
                mStatusMicro.setBackgroundColor(Color.YELLOW);
                mStatusMicro.setText(R.string.status_micro_connecting);
                break;
        }
    }
}