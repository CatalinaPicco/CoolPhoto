package com.example.coolphoto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coolphoto.models.Album;
import com.example.coolphoto.networking.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Album>> mutableLiveData;
    private NewsRepository newsRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = NewsRepository.getInstance();
        mutableLiveData = newsRepository.getNews();

    }

    public LiveData<List<Album>> getNewsRepository() {
        return mutableLiveData;
    }

}
