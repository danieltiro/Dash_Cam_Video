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
import com.rodimisas.dash_cam_video.Responce.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private Context context;
    private List<VideoItem> records;
    private int layout;
    private OnItemClickListener itemClickListener;

    public RecordAdapter(List<VideoItem> records, int layout, OnItemClickListener itemListener) {
        this.itemClickListener = itemListener;
        this.records = records;
        this.layout = layout;
    }

    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(records.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView event;
        public TextView fecha;
        public ImageView miniatura;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            event = (TextView) itemView.findViewById(R.id.event);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            miniatura = (ImageView) itemView.findViewById(R.id.miniatura);
        }

        public void bind(final VideoItem record, final OnItemClickListener itemListener) {
            name.setText(record.getDeviceName());
            event.setText(record.getEventType());
            fecha.setText(record.getCreateAt());
            Picasso.get().load(record.getMiniatura()).fit().into(miniatura);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(record, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(VideoItem equipo, int position);
    }
}
