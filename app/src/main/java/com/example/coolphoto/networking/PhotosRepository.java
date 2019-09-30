package com.example.coolphoto.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.coolphoto.models.PhotoItem;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosRepository {

    private static PhotosRepository photosRepository;

    public static PhotosRepository getInstance(){
        if (photosRepository == null){
            photosRepository = new PhotosRepository();
        }
        return photosRepository;
    }

    private INewsApi newsApi;

    public PhotosRepository(){
        newsApi = RetrofitService.createService(INewsApi.class);
    }

    public MutableLiveData<List<PhotoItem>> getPhotos(int id){
        MutableLiveData<List<PhotoItem>> photoData = new MutableLiveData<>();
        newsApi.getPhotoList(id).enqueue(new Callback<List<PhotoItem>>() {
            @Override
            public void onResponse(Call<List<PhotoItem>> call,
                                   Response<List<PhotoItem>> response) {
                Log.e("call", call.request().url().toString());
                if (response.isSuccessful()){
                    photoData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PhotoItem>> call, Throwable t) {
                Log.e("call", call.request().body().toString());
                photoData.setValue(null);
            }
        });
        return photoData;
    }
}
