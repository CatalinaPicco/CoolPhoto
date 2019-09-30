package com.example.coolphoto.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolphoto.R;
import com.example.coolphoto.adapters.PhotosAdapter;
import com.example.coolphoto.models.PhotoItem;
import com.example.coolphoto.viewmodels.PhotosViewModel;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    List<PhotoItem> photos;
    PhotosAdapter photosAdapter;
    RecyclerView rvHeadline;
    PhotosViewModel photosViewModel;
    LinearLayout noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        int sessionId = getIntent().getIntExtra("EXTRA_ID", 0);
        noInternet = findViewById(R.id.ll_no_internet);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvHeadline = findViewById(R.id.rvNews);
        photos = new ArrayList<>();
        photosViewModel =
                ViewModelProviders.of(this).get(PhotosViewModel.class);
        photosViewModel.setParam(sessionId);
        photosViewModel.init();
        photosViewModel.getPhotoRepository().observe(this, new Observer<List<PhotoItem>>() {
            @Override
            public void onChanged(List<PhotoItem> photosResponse) {
                if (photosResponse != null) {
                    List<PhotoItem> photosList = photosResponse;
                    photos.addAll(photosList);
                    photosAdapter.notifyDataSetChanged();
                } else {
                    rvHeadline.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                }
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
