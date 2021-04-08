package com.example.dogadoptionapp.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.dogadoptionapp.R;
import com.squareup.picasso.Picasso;

public class DetailedDogActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String sharedPrefFile = "com.example.dogadoptionapp";

    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    private Button adoptButton;
    private TextView nameText;
    private ImageView imageView;
    private TextView ageText;
    private TextView genderText;
    private TextView descriptionText;

    private Integer dogId;
    private Integer savedDogId;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dog);

        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        adoptButton = findViewById(R.id.adopt_button);
        nameText = findViewById(R.id.nameDetail);
        ageText = findViewById(R.id.ageDetail);
        genderText = findViewById(R.id.genderDetail);
        descriptionText = findViewById(R.id.descriptionDetail);
        imageView = findViewById(R.id.dogImageDetail);

        dogId = intent.getIntExtra("id", 0);
        nameText.setText(intent.getStringExtra("name"));
        ageText.setText(getString(R.string.age_text, String.valueOf(intent.getIntExtra("age", 0))));
        descriptionText.setText(intent.getStringExtra("description"));
        imageURL = intent.getStringExtra("pictureUrl");
        Picasso.get().load(imageURL).into(imageView);
        if (intent.getStringExtra("gender").equals("F")) {
            genderText.setText(R.string.female_text);
        } else {
            genderText.setText(R.string.male_text);
        }

        savedDogId = sharedPreferences.getInt("dogId" + dogId, -1);
        if (savedDogId == -1) {
            adoptButton.setText(R.string.adopt_text);
        } else {
            adoptButton.setText(R.string.discard_adoption_text);
        }

        createNotificationChannel();
    }

    public void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Dog Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from DogApp");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationsBuilder() {
        return new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(nameText.getText() + " was adopted!")
                .setSmallIcon(R.drawable.ic_adopt_dog);
    }

    public void adoptDog(View view) {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        AlertDialog.Builder adoptAlert = new AlertDialog.Builder(DetailedDogActivity.this);

        if (savedDogId == -1) {
            adoptAlert.setTitle("Are you sure you want to adopt this dog?");

            adoptAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    preferencesEditor.putInt("dogId" + dogId, dogId);
                    preferencesEditor.apply();

                    adoptButton.setText(R.string.discard_adoption_text);

                    NotificationCompat.Builder notifyBuilder = getNotificationsBuilder();
                    notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());

                    Intent toMain = new Intent(DetailedDogActivity.this, MainActivity.class);
                    DetailedDogActivity.this.startActivity(toMain);
                }
            });
        } else {
            adoptAlert.setTitle("Are you sure you want to discard the adoption?");

            adoptAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adoptButton.setText(R.string.adopt_text);

                    savedDogId = -1;
                    preferencesEditor.remove("dogId" + dogId);
                    preferencesEditor.apply();
                }
            });
        }

        adoptAlert.setNegativeButton("Cancel", (dialog, which) -> {

        });

        adoptAlert.show();
    }
}