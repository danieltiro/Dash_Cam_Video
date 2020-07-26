package com.rodimisas.dash_cam_video.Fragments;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rodimisas.dash_cam_video.Adapters.InfoMarkerAdapter;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.Equipment;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.EquipmentItemRuntime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapDevicesFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap gMap;
    private View rootView;
    private MapView mapView;
    private Marker marker;
    private Geocoder geocoder;
    private List<Address> addresses;
    private Equipment equipo;
    private List<Equipment> equipos = new ArrayList<>();
    private List<EquipmentItem> listEquipo;
    private LatLngBounds.Builder builder;
    private List<InfoMarkerAdapter> InfoMarkers;
    public MapDevicesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map_devices, container, false);
        equipos = (List<Equipment>) getArguments().getSerializable("equipos");
        for (int i=0 ; i<equipos.size(); i++){
            listEquipo = equipos.get(i).getEquipos();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.checkIfGPSIsEnabled();
        gMap = googleMap;
        builder = new LatLngBounds.Builder();
        gMap.setInfoWindowAdapter(new InfoMarkerAdapter(LayoutInflater.from(getActivity())));
        for (int i = 0; i < listEquipo.size(); i++ ) {

            float lat= listEquipo.get(i).getLatitud().getValue();
            float lng = listEquipo.get(i).getLongitud().getValue();

            if(lat > 0 || lng > 0){
                LatLng ubicacion = new LatLng(lat, lng);
                BitmapDescriptor icon;
                if (listEquipo.get(i).getStatus().contains("OFFLINE")) {
                    icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_offline);
                } else {
                    icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_online);
                }
                marker = gMap.addMarker(new MarkerOptions()
                        .position(ubicacion)
                        .title("ubicacion").draggable(true).icon(icon));
                marker.setTag(listEquipo.get(i));
                builder.include(marker.getPosition());
            }
        }
        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        LatLngBounds bounds = builder.build();
        int padding = 300; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        gMap.animateCamera(cu);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    private void checkIfGPSIsEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                showInfoAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Señal GPS")
                .setMessage("No tien señal de GPS activada. Desea activarla ahora?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private Bitmap getMarkerOnline(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_status, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.deviceStatus);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}