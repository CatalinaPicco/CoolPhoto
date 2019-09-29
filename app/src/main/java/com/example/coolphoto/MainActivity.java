package com.example.coolphoto;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coolphoto.adapters.NewsAdapter;
import com.example.coolphoto.models.Album;
import com.example.coolphoto.viewmodels.NewsViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.AlbumAdapterListener{

    List<Album> albums;
    NewsAdapter newsAdapter;
    RecyclerView rvHeadline;
    NewsViewModel newsViewModel;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rvHeadline = findViewById(R.id.rvNews);
        albums = new ArrayList<>();
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.init();
        newsViewModel.getNewsRepository().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> newsResponse) {
                List<Album> newsArticles = newsResponse;
                albums.clear();
                albums.addAll(newsArticles);
                newsAdapter.notifyDataSetChanged();
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
                newsAdapter.getFilter().filter(query);
                newsAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newsAdapter.getFilter().filter(newText);
                newsAdapter.notifyDataSetChanged();
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
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(this, albums, this);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAlbumSelected(Album album) {
        Toast.makeText(getApplicationContext(), "Selected: " + album.getTitle(), Toast.LENGTH_LONG).show();
    }
}
