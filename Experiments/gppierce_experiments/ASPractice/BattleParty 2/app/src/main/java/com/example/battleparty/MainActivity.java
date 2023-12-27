package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button2, button4, button8, button16, btnBack, btnContinue;
    EditText txtPartyName;
    private int players = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button8 = findViewById(R.id.button8);
        button16 = findViewById(R.id.button16);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        txtPartyName = findViewById(R.id.txtPartyName);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players = 2;
                setButtonBackground(button2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players = 4;
                setButtonBackground(button4);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players = 8;
                setButtonBackground(button8);
            }
        });

        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players = 16;
                setButtonBackground(button16);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String partyName = txtPartyName.getText().toString();
                if (partyName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "You have not entered the party name", Toast.LENGTH_LONG).show();
                } else if (players == 0) {
                    Toast.makeText(getApplicationContext(), "You have not selected the number of players", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject data = new JSONObject();
                    try {
                        data.put("party_name", partyName);
                        data.put("players", players);
                        String dataInJson = data.toString();
                        Toast.makeText(getApplicationContext(), "Data to be sent \n" + dataInJson, Toast.LENGTH_LONG).show();

                        // Start PlayerIDActivity and pass the JSON data as a string
                        Intent intent = new Intent(getApplicationContext(), PlayerIDActivity.class);
                        intent.putExtra("obj", dataInJson);
                        startActivity(intent);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BackActivity.class));
            }
        });
    }

    private void setButtonBackground(Button clickedButton) {
        Button[] buttons = {button2, button4, button8, button16};
        for (Button button : buttons) {
            if (button == clickedButton) {
                button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                button.setBackgroundColor(0); // Reset other buttons
            }
        }
    }
}
