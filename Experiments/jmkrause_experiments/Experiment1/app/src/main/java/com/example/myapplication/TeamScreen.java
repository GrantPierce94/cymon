package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_team_screen);

        TextView teamName = findViewById(R.id.team_textView_name);
        ImageView charmander = findViewById(R.id.team_imageView_charmander);
        ImageView mewTwo = findViewById(R.id.team_imageView_mewtwo);
        ImageView pikachu = findViewById(R.id.team_imageView_pikachu);
        ImageView eevee = findViewById(R.id.team_imageView_eevee);

        charmander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName.setText("CHARMANDER");
                teamName.setVisibility(View.VISIBLE);
            }
        });

        mewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName.setText("MEWTWO");
                teamName.setVisibility(View.VISIBLE);
            }
        });

        pikachu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName.setText("PIKACHU");
                teamName.setVisibility(View.VISIBLE);
            }
        });

        eevee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName.setText("EEVEE");
                teamName.setVisibility(View.VISIBLE);
            }
        });



    }
}