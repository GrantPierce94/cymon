package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        Button activeParty = findViewById(R.id.btnActiveParty);
        Button btnLeaderboards = findViewById(R.id.btnLeaderboards);
        Button btnPartyCreator = findViewById(R.id.btnPartyCreator);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnFriends = findViewById(R.id.btnFriends);
        Button btnStore = findViewById(R.id.btnStore);
        Button btnPartyChat = findViewById(R.id.btnPartyChat); // Add this line

        activeParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), JoinExistingParty.class));
            }
        });

        btnLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Leaderboards button click
                // You can open a new activity or perform any desired action here
                startActivity(new Intent(view.getContext(), LeaderBoard.class));
            }
        });

        btnPartyCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the BattleParty activity when Party Creator button is clicked
                Intent intent = new Intent(MainScreen.this, BattleParty.class);
                startActivity(intent);
            }
        });

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Friends.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignInActivity when the Back button is clicked
                Intent intent = new Intent(MainScreen.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Store.class));
            }
        });

        btnPartyChat.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View view) {
                // Start the PartyChat activity when Party Chat button is clicked
                Intent intent = new Intent(MainScreen.this, PartyChat.class);
                startActivity(intent);
            }
        });
    }
}
