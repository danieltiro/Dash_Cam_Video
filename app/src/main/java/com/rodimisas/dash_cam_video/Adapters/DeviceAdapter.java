package com.rodimisas.dash_cam_video.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.rodimisas.dash_cam_video.R;
import com.rodimisas.dash_cam_video.Responce.EquipmentItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder>{
    private Context context;
    private List<EquipmentItem> equipos;
    private int layout;
    private OnItemClickListener itemClickListener;

    public DeviceAdapter(List<EquipmentItem> equipos, int layout,OnItemClickListener itemListener) {
        this.itemClickListener = itemListener;
        this.equipos = equipos;
        this.layout = layout;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(equipos.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView imei;
        public TextView status;
        public TextView url;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            imei = (TextView) itemView.findViewById(R.id.imei);
            status = (TextView) itemView.findViewById(R.id.status);
            image = (ImageView) itemView.findViewById(R.id.imagePhoto);
        }

        public void bind(final EquipmentItem equipos,final OnItemClickListener itemListener) {
            name.setText(equipos.getDeviceName());
            imei.setText(equipos.getImei());
            status.setText(equipos.getStatus());
            if (equipos.getStatus().contains("OFFLINE")){
                Picasso.get().load(R.mipmap.ic_marker_offline).fit().into(image);
            }else{
                Picasso.get().load(R.mipmap.ic_marker_online).fit().into(image);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(equipos, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(EquipmentItem equipo, int position);
    }
}
