package com.example.battlescreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView pokemonImage;
    private ProgressBar pokemonHP;
    private ProgressBar enemyHP;
    private Button attack1Button;
    private Button attack2Button;
    private Button attack3Button;
    private Button attack4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        pokemonImage = findViewById(R.id.pokemonImage);
        pokemonHP = findViewById(R.id.pokemonHP);
        enemyHP = findViewById(R.id.enemyHP);
        attack1Button = findViewById(R.id.attack1Button);
        attack2Button = findViewById(R.id.attack2Button);
        attack3Button = findViewById(R.id.attack3Button);
        attack4Button = findViewById(R.id.attack4Button);

        // Set click listeners for attack buttons
        attack1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack("Attack 1");
            }
        });

        attack2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack("Attack 2");
            }
        });

        attack3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack("Attack 3");
            }
        });

        attack4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack("Attack 4");
            }
        });
    }

    private void performAttack(String attackName) {
        // Implement your attack logic here
        // Update HP bars and handle the battle outcome
        Toast.makeText(this, "Used " + attackName, Toast.LENGTH_SHORT).show();
    }
}