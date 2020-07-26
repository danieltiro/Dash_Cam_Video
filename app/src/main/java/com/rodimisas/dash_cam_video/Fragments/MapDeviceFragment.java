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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rodimisas.dash_cam_video.Adapters.InfoMarkerAdapter;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.rodimisas.dash_cam_video.Responce.EquipmentItemRuntime;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapDeviceFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap gMap;
    private View rootView;
    private MapView mapView;
    private Marker marker;
    private Geocoder geocoder;
    private EquipmentItem equipo;

    public MapDeviceFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map_device, container, false);
        equipo = (EquipmentItem) getArguments().getSerializable("equipo");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        gMap = googleMap;
        gMap.setInfoWindowAdapter(new InfoMarkerAdapter(LayoutInflater.from(getActivity())));
        this.checkIfGPSIsEnabled();
        // Add a marker in Sydney and move the camera
        EquipmentItemRuntime runtimelat = equipo.getLatitud();
        EquipmentItemRuntime runtimelng = equipo.getLongitud();

        LatLng ubicacion = new LatLng(runtimelat.getValue(), runtimelng.getValue());

        BitmapDescriptor icon;
        if (equipo.getStatus().contains("OFFLINE")) {
            icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_offline);
        } else {
            icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_online);
        }
        marker = gMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title("ubicacion").draggable(true).icon(icon));
        marker.setTag(equipo);
        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16.0f));

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