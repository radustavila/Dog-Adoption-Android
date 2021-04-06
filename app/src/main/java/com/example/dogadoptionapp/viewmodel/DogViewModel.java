package com.example.dogadoptionapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dogadoptionapp.model.Dog;
import com.example.dogadoptionapp.repository.DogRepository;

import java.util.List;

public class DogViewModel extends AndroidViewModel {

    private DogRepository dogRepository;
    private LiveData<List<Dog>> dogList;

    public DogViewModel(@NonNull Application application) {
        super(application);

        dogRepository = new DogRepository();
        dogList = dogRepository.getDoggos();
    }

    public LiveData<List<Dog>> getDogList() {
        return dogList;
    }
}
