package com.example.dogadoptionapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.dogadoptionapp.R;
import com.example.dogadoptionapp.adapter.DogsAdapter;
import com.example.dogadoptionapp.viewmodel.DogViewModel;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private DogViewModel dogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final DogsAdapter dogsAdapter = new DogsAdapter(this);
        recyclerView.setAdapter(dogsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        dogViewModel.getDogList().observe(this, dogsAdapter::setDogsList);
    }
}