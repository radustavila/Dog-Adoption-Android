package com.example.dogadoptionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogadoptionapp.R;
import com.example.dogadoptionapp.model.Dog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {

    private List<Dog> dogsList;
    private Context mContext;

    public DogsAdapter(Context context) {
        this.mContext = context;
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

        private TextView nameText;
        private TextView genderText;
        private TextView ageText;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            genderText = itemView.findViewById(R.id.gender);
            ageText = itemView.findViewById(R.id.age);
            imageView = itemView.findViewById(R.id.dogImage);

            itemView.setOnClickListener(this);
        }

        void bindTo(Dog currentDog) {
            nameText.setText(currentDog.getName());
            if (currentDog.getGender().equals("F")) {
                genderText.setText("Female");
            } else {
                genderText.setText("Male");
            }
            ageText.setText(mContext.getString(R.string.age_text, String.valueOf(currentDog.getAge())));
            Picasso.get().load(currentDog.getImageUrl()).into(imageView);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
