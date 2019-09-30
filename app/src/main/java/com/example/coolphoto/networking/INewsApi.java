package com.example.coolphoto.networking;

import com.example.coolphoto.models.PhotoItem;
import com.example.coolphoto.models.Album;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INewsApi {

    @GET("/photos")
    Call<List<PhotoItem>> getPhotoList(@Query("albumId") int idAlbum);

    @GET("/albums/")
    Call<List<Album>> getNewsList();

}