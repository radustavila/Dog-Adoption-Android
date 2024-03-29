package com.example.dogadoptionapp.networking;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dogadoptionapp.model.Dog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DogNetwork {

    private static final String TAG = "DOG_NETWORK";

    public static void getDogsList(String filter, AFNCallback afnCallback) {
        List<Dog> dawgs = new ArrayList<>();

        AndroidNetworking
                .get("https://run.mocky.io/v3/d230b290-00b5-4727-adfc-5572662b9d0b")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray dogsArray = response.getJSONArray("dogs");

                            for (int i = 0; i < dogsArray.length(); i++) {
                                JSONObject dawg = dogsArray.getJSONObject(i);
                                Dog dog = new Dog(dawg.getInt("id"),
                                        dawg.getString("name"),
                                        dawg.getString("gender"),
                                        dawg.getInt("age"),
                                        dawg.getString("description"),
                                        dawg.getString("breed"),
                                        dawg.getString("url"));

                                if (dog.getBreed().contains(filter))
                                    dawgs.add(dog);
                                else if (filter.equals(""))
                                    dawgs.add(dog);
                            }
                            afnCallback.onSuccess(dawgs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                        afnCallback.onFailure(error.getErrorDetail());
                    }
                });
    }

}
