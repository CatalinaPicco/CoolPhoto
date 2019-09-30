package com.example.coolphoto.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.R;
import com.example.coolphoto.models.Album;

import java.util.ArrayList;
import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.NewsViewHolder> implements Filterable {

    Context context;
    List<Album> albums;
    List<Album> contactListFiltered;
    AlbumAdapterListener listener;


    public AlbumAdapter(Context context, List<Album> albums, AlbumAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.albums = albums;
        this.contactListFiltered = albums;
    }

    @NonNull
    @Override
    public AlbumAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        return new  NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.NewsViewHolder holder, final int position) {
        final Album album = contactListFiltered.get(position);
        String ids = String.valueOf(album.id);
        holder.tvName.setText(ids);
        holder.tvDesCription.setText(album.title);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = albums;
                } else {
                    List<Album> filteredList = new ArrayList<>();
                    for (Album row : albums) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Album>) filterResults.values;
                for (Album row : contactListFiltered){
                    Log.e("filtro:", row.title);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvDesCription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesCription = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAlbumSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public interface AlbumAdapterListener {
        void onAlbumSelected(Album album);
    }

}
