package com.example.coolphoto.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.coolphoto.R;
import com.example.coolphoto.adapters.AlbumAdapter;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.viewmodels.AlbumViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumAdapter.AlbumAdapterListener{

    private static final String EXTRA_MESSAGE = null;
    List<Album> albums;
    AlbumAdapter albumAdapter;
    RecyclerView rvHeadline;
    AlbumViewModel albumViewModel;
    SearchView searchView;
    LinearLayout noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvHeadline = findViewById(R.id.rvNews);
        noInternet = findViewById(R.id.ll_no_internet);
        albums = new ArrayList<>();
        albumViewModel =
                ViewModelProviders.of(this).get(AlbumViewModel.class);
        albumViewModel.init();
        albumViewModel.getAlbumRepository().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> newsResponse) {
                if (newsResponse != null){
                    List<Album> newsArticles = newsResponse;
                    albums.clear();
                    albums.addAll(newsArticles);
                    albumAdapter.notifyDataSetChanged();
                } else {
                    rvHeadline.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                }
            }
        });

        setupRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                albumAdapter.getFilter().filter(query);
                albumAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                albumAdapter.getFilter().filter(newText);
                albumAdapter.notifyDataSetChanged();
                //Toast.makeText(MainActivity.this, "searching" + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(this, albums, this);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(albumAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            albumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAlbumSelected(Album album) {
        Toast.makeText(this, "Selección: " + album.getId(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("EXTRA_ID", album.getId());
        startActivity(intent);
    }
}
