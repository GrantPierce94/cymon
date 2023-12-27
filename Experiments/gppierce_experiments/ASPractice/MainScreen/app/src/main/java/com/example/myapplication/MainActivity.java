package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLeaderboards = findViewById(R.id.btnLeaderboards);
        Button btnPartyCreator = findViewById(R.id.btnPartyCreator);
        Button btnBack = findViewById(R.id.btnBack);

        Button btnCompetitiveBattle = findViewById(R.id.btnCompetitiveBattle);
        Button btnPartyChat = findViewById(R.id.btnPartyChat);

        btnLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Leaderboards button click
                // You can open a new activity or perform any desired action here
                // startActivity(new Intent(view.getContext(), LeaderBoard.class));
            }
        });

        btnPartyCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the BattleParty activity when Party Creator button is clicked
                //Intent intent = new Intent(MainScreen.this, BattleParty.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignInActivity when the Back button is clicked
                //Intent intent = new Intent(MainScreen.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnCompetitiveBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Competitive Battle button click
                // You can open a new activity or perform any desired action here
                // startActivity(new Intent(view.getContext(), CompetitiveBattle.class));
            }
        });

        btnPartyChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Party Chat button click
                // You can open a new activity or perform any desired action here
                // startActivity(new Intent(view.getContext(), PartyChat.class));
            }
        });
    }
}
