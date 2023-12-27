package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.battleparty.WebSockets.UtilEnum;
import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.java_websocket.handshake.ServerHandshake;


public class BattleParty extends AppCompatActivity implements WebSocketListener {
    Button button2, button4, button8, button16, btnBack, btnContinue;
    EditText txtPartyName;
    private int players = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battleparty);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button8 = findViewById(R.id.button8);
        button16 = findViewById(R.id.button16);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);
        txtPartyName = findViewById(R.id.txtPartyName);

        WebSocketManager.getInstance().setWebSocketListener(BattleParty.this);
        String currentPlayerName = WebSocketManager.getPlayer().getName();

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

                    String toSend = "createParty " + partyName + " " + players + " " + currentPlayerName;
                    WebSocketManager.getInstance().sendMessage(toSend, UtilEnum.CONTROLLER.PARTYCONTROLLER);
                    try {
                        Intent intent = new Intent(getApplicationContext(), PlayerIDActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the MainScreen activity
                startActivity(new Intent(getApplicationContext(), MainScreen.class));
                // Finish the current activity to prevent going back to it from the MainScreen
                finish();
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

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        runOnUiThread(() -> {
            
        });
    }


    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), "---\nconnection closed by " + closedBy + "\nreason: " + reason, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
