package com.example.coolphoto.adapters;

import android.content.Context;
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


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Filterable {

    Context context;
    ArrayList<Album> albums;
    ArrayList<Album> contactListFiltered;

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
                            if (row.title.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        albums = (ArrayList<Album>) filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = albums;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                   albums = (ArrayList<Album>) filterResults.values;
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
        }
    }


}
