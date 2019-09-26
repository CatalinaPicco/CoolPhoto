package com.example.coolphoto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.R;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<Album> albums;

    public NewsAdapter(Context context, ArrayList<Album> articles) {
        this.context = context;
        this.albums = articles;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        String ids = String.valueOf(albums.get(position).id);
        holder.tvName.setText(ids);
        holder.tvDesCription.setText(albums.get(position).title);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDesCription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesCription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
