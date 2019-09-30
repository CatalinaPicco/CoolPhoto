package com.example.coolphoto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coolphoto.models.PhotoItem;
import com.example.coolphoto.networking.PhotosRepository;

import java.util.List;

public class PhotosViewModel extends ViewModel{

        private MutableLiveData<List<PhotoItem>> mutablePhotoLiveData;
        private PhotosRepository photosRepository;
        public int para;

        public void setParam(int param){
            para = param;
        }

        public void init(){
            if (mutablePhotoLiveData != null){
                return;
            }
            photosRepository = PhotosRepository.getInstance();
            mutablePhotoLiveData = photosRepository.getPhotos(para);

        }

        public LiveData<List<PhotoItem>> getPhotoRepository() {
            return mutablePhotoLiveData;
        }

}
