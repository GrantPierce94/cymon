package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FightScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        //team is the string of chosen character. "selected" is the key
        String team = intent.getStringExtra("selected");
//        Toast.makeText(getApplicationContext(), team, Toast.LENGTH_SHORT).show();

        Button attack = findViewById(R.id.main_button_charAttack);
        ImageView teamImg = findViewById(R.id.main_imageView_team);

        //imageResource gets the R.drawable for the image we want teamImg to be
        int imageResource = intent.getIntExtra(team, 0);
        if (imageResource !=0) {
            teamImg.setImageResource(imageResource);
        }

        ImageView opponent = findViewById(R.id.main_imageView_eevee);
        Handler handler = new Handler();
        int animSpacing = 3000;

        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation zoomAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.zoom);
                Animation blinkAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.blink);

                teamImg.startAnimation(zoomAnim);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        opponent.startAnimation(blinkAnim);
                    }
                }, animSpacing);
            }
        });
    }
}