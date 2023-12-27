package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class TeamSelection extends AppCompatActivity {

    CheckBox charmander;
    CheckBox pikachu;
    CheckBox eevee;
    CheckBox mewtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_team_selection);

        Button selected = findViewById(R.id.team_button_continue);
        charmander = findViewById(R.id.team_checkBox_charmander);
        pikachu = findViewById(R.id.team_checkBox_pikachu);
        eevee = findViewById(R.id.team_checkBox_eevee);
        mewtwo = findViewById(R.id.team_checkBox_mewtwo);

        charmander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioFunction(charmander);
                selected.setText(R.string.charmander);
            }
        });

        pikachu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioFunction(pikachu);
                selected.setText(R.string.pikachu);
            }
        });

        eevee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioFunction(eevee);
                selected.setText(R.string.eevee);
            }
        });

        mewtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioFunction(mewtwo);
                selected.setText(R.string.mewtwo);
            }
        });

        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FightScreen.class);
                intent.putExtra("selected", selected.getText().toString());

                intent.putExtra("Pikachu", R.drawable.pikachu);
                intent.putExtra("Eevee", R.drawable.eevee);
                intent.putExtra("Charmander", R.drawable.charmander);
                intent.putExtra("Mewtwo", R.drawable.mewtwo);

                startActivity(intent);
            }
        });

    }

    private void radioFunction(CheckBox selected) {
        CheckBox[] boxes = {charmander, pikachu, eevee, mewtwo};
        for (CheckBox b : boxes) {
            if (!(b.equals(selected))) {
                b.setChecked(false);
            }
        }
    }
}