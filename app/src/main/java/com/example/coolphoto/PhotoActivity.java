package com.example.coolphoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.adapters.NewsAdapter;
import com.example.coolphoto.adapters.PhotosAdapter;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.models.PhotoItem;
import com.example.coolphoto.viewmodels.NewsViewModel;
import com.example.coolphoto.viewmodels.PhotosViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class PhotoActivity extends AppCompatActivity {

    List<PhotoItem> photos;
    PhotosAdapter photosAdapter;
    RecyclerView rvHeadline;
    PhotosViewModel photosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        int sessionId = getIntent().getIntExtra("EXTRA_ID", 0);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvHeadline = findViewById(R.id.rvNews);
        photos = new ArrayList<>();
        photosViewModel =
                ViewModelProviders.of(this).get(PhotosViewModel.class);
        photosViewModel.setParam(sessionId);
        photosViewModel.init();
        photosViewModel.getPhotoRepository().observe(this, new Observer<List<PhotoItem>>() {
            @Override
            public void onChanged(List<PhotoItem> photosResponse) {
                List<PhotoItem> photosList = photosResponse;
                photos.addAll(photosList);
                photosAdapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        if (photosAdapter == null) {
            photosAdapter = new PhotosAdapter(this, photos);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(photosAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            photosAdapter.notifyDataSetChanged();
        }
    }

}
