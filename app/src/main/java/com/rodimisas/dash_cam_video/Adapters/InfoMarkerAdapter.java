package com.rodimisas.dash_cam_video.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.squareup.picasso.Picasso;

public class InfoMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "InfoMarkerAdapter";
    private Context context;
    private LayoutInflater inflater;
    private EquipmentItem equipo;

    public InfoMarkerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        equipo = (EquipmentItem) marker.getTag();
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.info_marker, null);
        String[] info = marker.getTitle().split("&");
        String url = marker.getSnippet();
        ImageView image = (ImageView) v.findViewById(R.id.info_window_imagen);
        if (equipo.getStatus().contains("OFFLINE")){
            image.setImageResource(R.mipmap.ic_marker_offline);
        }else{
            image.setImageResource(R.mipmap.ic_marker_online);
        }
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText(equipo.getDeviceName());
        ((TextView)v.findViewById(R.id.info_window_placas)).setText(equipo.getImei());
        ((TextView)v.findViewById(R.id.info_window_estado)).setText(equipo.getStatus());
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
