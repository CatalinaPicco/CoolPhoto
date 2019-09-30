package com.example.coolphoto.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.R;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.models.PhotoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {

    Context context;
    List<PhotoItem> photos;

    public PhotosAdapter(Context context, List<PhotoItem> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotosAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);
        return new  PhotosViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.PhotosViewHolder holder, int position) {
        final PhotoItem photoItem = photos.get(position);
        holder.tvName.setText(photos.get(position).getId());
        holder.tvDesCription.setText(photos.get(position).title);

        Picasso.get()
                .load(photoItem.getThumbnailUrl())
                .into(holder.ivPhoto);
    }


    public class PhotosViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPhoto;
        TextView tvName;
        TextView tvDesCription;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            tvName = itemView.findViewById(R.id.tvIdPhoto);
            tvDesCription = itemView.findViewById(R.id.tvDescriptionPhoto);
        }


    }

}
