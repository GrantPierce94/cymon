package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.cymonbattle.R;

import java.util.List;

public class Friends extends AppCompatActivity implements WebSocketListener {
    final String BASE_URL = "ws://10.0.2.2:8080/";
    final String URL = "http://10.0.2.2:8080/";

    Button addPlayer;
    Button getFriends;
    Chip chip1;
    Chip chip2;
    Chip chip3;
    Chip chip4;
    Chip chip5;
    Chip chip6;
    ChipGroup allChips;
    TextView title;
    EditText userInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        chip1 = findViewById(R.id.chip1);
        chip2 = findViewById(R.id.chip2);
        chip3 = findViewById(R.id.chip3);
        chip4 = findViewById(R.id.chip4);
        chip5 = findViewById(R.id.chip5);
        chip6 = findViewById(R.id.chip6);
        allChips = findViewById(R.id.chipGroupFriends);
        addPlayer = findViewById(R.id.btn_search);
        getFriends = findViewById(R.id.btn_send);
        title = findViewById(R.id.textView_title);
        userInput = findViewById(R.id.editText_input);

        WebSocketManager.getInstance().setWebSocketListener(Friends.this);
        String currentPlayerName = WebSocketManager.getPlayer().getName();
        String serverUrl = BASE_URL + "player/" + currentPlayerName + "/";
        WebSocketManager.getInstance().connectWebSocket(serverUrl);


        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInput.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Enter a name to search", Toast.LENGTH_LONG).show();
                    Log.d("[addPlayer] ", "empty user input error");
                }

                String playerToSearch = userInput.getText().toString().trim();
                if(currentPlayerName!=null) {
                    Log.d("[addPlayer] ", currentPlayerName);
                }
                WebSocketManager.getInstance().sendMessage("add " + playerToSearch);
            }
        });

        getFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send message to retrieve current friends
                WebSocketManager.getInstance().sendMessage("getFriends");
            }
        });

    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onWebSocketMessage(String message) {
        if (message.contains("added")) {
            Log.d("Received message", message);
        } else {
            try {
                JSONArray obj = new JSONArray(message);
                showFriends(obj);
            } catch (JSONException e) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onWebSocketError(Exception ex) {
    }

    public void showFriends(JSONArray message) {
        runOnUiThread(() -> {
            try {
                Button tempButton = null;
                for (int i = 0; i < Math.min(message.length(), 7); i++) {
                    switch (i) {
                        case 0:
                            tempButton = chip1;
                            break;
                        case 1:
                            tempButton = chip2;
                            break;
                        case 2:
                            tempButton = chip3;
                            break;
                        case 3:
                            tempButton = chip4;
                            break;
                        case 4:
                            tempButton = chip5;
                            break;
                        case 5:
                            tempButton = chip6;
                            break;
                    }
                    int friendNum = i+1;
                    String str = "Friend " + friendNum + ": " + message.getString(i);
                    tempButton.setText(str);
//                    tempButton.setTextColor(Color.parseColor(""))
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}