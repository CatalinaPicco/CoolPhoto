package com.example.coolphoto.networking;

import com.example.coolphoto.models.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface INewsApi {
    @GET("/albums/")
    Call<List<Album>> getNewsList();
}