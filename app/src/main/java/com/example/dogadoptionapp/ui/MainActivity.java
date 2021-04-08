package com.example.dogadoptionapp.ui;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogadoptionapp.R;
import com.example.dogadoptionapp.adapter.DogsAdapter;
import com.example.dogadoptionapp.viewmodel.DogViewModel;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private DogViewModel dogViewModel;

    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recyclerView);

        final DogsAdapter dogsAdapter = new DogsAdapter(this);
        recyclerView.setAdapter(dogsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        dogViewModel.getDogList().observe(this, dogsAdapter::setDogsList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filter) {
                dogViewModel.setFilter(filter);
                return false;
            }
        });
    }
}