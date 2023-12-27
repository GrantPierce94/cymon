package com.example.battleparty.Multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.cymonbattle.R;
import com.example.cymonbattle.battle.MainBattleMenuFragment;

public class MultiPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);
    }
}