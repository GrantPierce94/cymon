package com.example.leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leaderboard.Logic.RegistrationLogic;
import com.example.leaderboard.Network.ServerRequest;

public class EditScores extends AppCompatActivity implements IView {

    Button addScore;
    EditText id;
    EditText score;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scores);

        addScore = findViewById(R.id.editScores_button_add);
        id = findViewById(R.id.editScores_editText_id);
        score = findViewById(R.id.editScores_editText_score);
        data = findViewById(R.id.editScores_textView_data);

        ServerRequest serverRequest = new ServerRequest();
        final RegistrationLogic logic = new RegistrationLogic(this, serverRequest);

        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int playerId = Integer.parseInt(id.getText().toString());
                    int gameScore = Integer.parseInt(score.getText().toString());
                    logic.registerGame(playerId, gameScore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }//oncreate

    @Override
    public void showText(String s) {
        data.setText(s);
    }

    @Override
    public void toastText(String s) {
        data.setText(s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}