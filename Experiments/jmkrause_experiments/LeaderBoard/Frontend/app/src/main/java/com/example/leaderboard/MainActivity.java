package com.example.leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leaderboard.Logic.RegistrationLogic;
import com.example.leaderboard.Network.ServerRequest;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IView{
    Button enterScore;
    Button go;
    Button advanceToNxt;
    EditText score;
    EditText name;
    public TextView dataShow;
    ArrayList<Integer> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_main);

        enterScore = findViewById(R.id.main_button_enterScore);
        go = findViewById(R.id.main_button_go);
        score = findViewById(R.id.main_editText_score);
        name = findViewById(R.id.main_editText_name);
        dataShow = findViewById(R.id.main_textView_data);
        scores = new ArrayList<Integer>();
        advanceToNxt = findViewById(R.id.main_button_advanceToNxt);

        ServerRequest serverRequest = new ServerRequest();
        final RegistrationLogic logic = new RegistrationLogic(this, serverRequest);

        enterScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer inputScore = Integer.parseInt(score.getText().toString());
                    scores.add(inputScore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                if (!TextUtils.isEmpty(nameStr) && scores != null) {
                    try {
                        logic.registerGameAndPlayer(nameStr, scores);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        advanceToNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), EditScores.class));
            }
        });


    }//oncreate

    @Override
    public void showText(String s) {
        dataShow.setText(s);
        dataShow.setVisibility(View.VISIBLE);
    }

    @Override
    public void toastText(String s) {
        dataShow.setText(s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}