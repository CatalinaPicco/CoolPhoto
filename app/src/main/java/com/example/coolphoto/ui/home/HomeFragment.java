package com.example.coolphoto.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.R;
import com.example.coolphoto.adapters.NewsAdapter;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.viewmodels.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ArrayList<Album> albums = new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView rvHeadline;
    NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rvHeadline = root.findViewById(R.id.rvNews);

        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.init();
        newsViewModel.getNewsRepository().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> newsResponse) {
                List<Album> newsArticles = newsResponse;
                albums.addAll(newsArticles);
                newsAdapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(getContext(), albums);
            rvHeadline.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }
}