package com.example.dogadoptionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogadoptionapp.R;
import com.example.dogadoptionapp.model.Dog;
import com.example.dogadoptionapp.ui.DetailedDogActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {

    private List<Dog> dogsList;

    private final Context mContext;
    private final SharedPreferences sharedPreferences;
    private final String sharedPrefFile = "com.example.dogadoptionapp";

    public DogsAdapter(Context context) {
        this.mContext = context;
        this.sharedPreferences = mContext.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DogsAdapter.ViewHolder holder, int position) {
        Dog currentDog = dogsList.get(position);
        holder.bindTo(currentDog);
    }

    public void setDogsList(List<Dog> doggos){
        this.dogsList = doggos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dogsList != null ? dogsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView nameText;
        private final TextView genderText;
        private final TextView ageText;
        private final ImageView imageView;
        private final TextView checkView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            genderText = itemView.findViewById(R.id.gender);
            ageText = itemView.findViewById(R.id.age);
            imageView = itemView.findViewById(R.id.dogImage);
            checkView = itemView.findViewById(R.id.check);

            itemView.setOnClickListener(this);
        }

        void bindTo(Dog currentDog) {
            nameText.setText(currentDog.getName());
            if (currentDog.getGender().equals("F")) {
                genderText.setText(R.string.female_text);
            } else {
                genderText.setText(R.string.male_text);
            }
            ageText.setText(mContext.getString(R.string.age_text, String.valueOf(currentDog.getAge())));
            Picasso.get().load(currentDog.getImageUrl()).into(imageView);

            int savedDogId = sharedPreferences.getInt("dogId" + currentDog.getId(), -1);
            if (savedDogId != -1) {
                Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/fa-regular-400.ttf");
                checkView.setTypeface(tf);
                checkView.setText(R.string.fa_icon_check);
                checkView.setVisibility(View.VISIBLE);
            } else {
                checkView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Dog currentDog = dogsList.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, DetailedDogActivity.class);
            detailIntent.putExtra("name", currentDog.getName());
            detailIntent.putExtra("age", currentDog.getAge());
            detailIntent.putExtra("gender", currentDog.getGender());
            detailIntent.putExtra("pictureUrl", currentDog.getImageUrl());
            detailIntent.putExtra("description", currentDog.getDescription());
            detailIntent.putExtra("id", currentDog.getId());
            mContext.startActivity(detailIntent);
        }
    }

}
