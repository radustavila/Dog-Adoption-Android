package com.example.dogadoptionapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogadoptionapp.model.Dog;
import com.example.dogadoptionapp.networking.AFNCallback;
import com.example.dogadoptionapp.networking.DogNetwork;

import java.util.List;

public class DogRepository {

    private static final String TAG = "DOG_REPOSITORY";
    private final MutableLiveData<List<Dog>> doggos;

    public DogRepository() {
        doggos = new MutableLiveData<>();

        DogNetwork.getDogsList(new AFNCallback() {
            @Override
            public void onSuccess(Object result) {
                doggos.setValue((List<Dog>) result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public LiveData<List<Dog>> getDoggos() {
        return doggos;
    }
}
