package com.example.coolphoto.networking;

import androidx.lifecycle.MutableLiveData;

import com.example.coolphoto.models.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private static AlbumRepository albumRepository;

    public static AlbumRepository getInstance(){
        if (albumRepository == null){
            albumRepository = new AlbumRepository();
        }
        return albumRepository;
    }

    private IServicesApi newsApi;

    public AlbumRepository(){
        newsApi = RetrofitService.createService(IServicesApi.class);
    }

    public MutableLiveData<List<Album>> getNews(){
        MutableLiveData<List<Album>> newsData = new MutableLiveData<>();
        newsApi.getNewsList().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call,
                                   Response<List<Album>> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                newsData.setValue(null);

            }
        });
        return newsData;
    }
}
