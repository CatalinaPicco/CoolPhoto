package com.example.coolphoto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coolphoto.models.Album;
import com.example.coolphoto.networking.AlbumRepository;

import java.util.List;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<List<Album>> mutableLiveData;
    private AlbumRepository albumRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        albumRepository = AlbumRepository.getInstance();
        mutableLiveData = albumRepository.getNews();

    }

    public LiveData<List<Album>> getAlbumRepository() {
        return mutableLiveData;
    }

}
