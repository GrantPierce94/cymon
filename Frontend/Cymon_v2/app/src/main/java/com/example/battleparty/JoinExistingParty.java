package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;

import org.json.JSONObject;

import org.java_websocket.handshake.ServerHandshake;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import com.example.cymonbattle.R;

public class JoinExistingParty extends AppCompatActivity implements WebSocketListener {
    final String BASE_URL = "ws://10.0.2.2:8080/";
    final String URL = "http://10.0.2.2:8080/";

    TextView dataContainer;
    Button getPartyBtn;
    Button join;
    RadioGroup radioGroup;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    RadioButton radio4;
    RadioButton radio5;
    private static int partyId;
    private static Map<Button, String> idNameMap;
    private static Map<Button, Integer> idRoomLeftMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_existing_party);

        getPartyBtn = findViewById(R.id.party_btn_getParties);
        radioGroup = findViewById(R.id.radioGroup);
        join = findViewById(R.id.party_btn_join);
        radio1 = findViewById(R.id.radio_one);
        radio2 = findViewById(R.id.radio_two);
        radio3 = findViewById(R.id.radio_three);
        radio4 = findViewById(R.id.radio_four);
        radio5 = findViewById(R.id.radio_five);
        partyId = 0;

        idNameMap = new Hashtable<>();
        idRoomLeftMap = new Hashtable<>();

        WebSocketManager.getInstance().setWebSocketListener(JoinExistingParty.this);
        String currentPlayerName = WebSocketManager.getPlayer().getName();
        String serverUrl = BASE_URL + "party/" + currentPlayerName + "/";
        WebSocketManager.getInstance().connectWebSocket(serverUrl);


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idB = radioGroup.getCheckedRadioButtonId();
                Button temp = findViewById(idB);
                String buttonText = temp.getText().toString();
                if (!buttonText.isEmpty()) {
                    Log.d("[Selected Button] ", buttonText);
                    int roomLeft = 0;
                    if (idRoomLeftMap.containsKey(temp)) {
                        roomLeft = idRoomLeftMap.get(temp);
                    }
                    if (roomLeft >0) {
                        String partyName = idNameMap.get(temp);
                        WebSocketManager.getInstance().sendMessage("joinParty " + currentPlayerName + " " + partyName);
                    }
                }
            }
        });

        getPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebSocketManager.getInstance().sendMessage("getParties");
            }
        });


    }//oncreate

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            try {
                JSONObject obj = new JSONObject(message);
                Iterator<String> keys = obj.keys();
                String partyName = "";
                int spotsLeft = 0;
                Button tempButton = null;
                partyId++;

                while(keys.hasNext()) {
                    switch(partyId) {
                        case 1:
                            tempButton = radio1;
                            break;
                        case 2:
                            tempButton = radio2;
                            break;
                        case 3:
                            tempButton = radio3;
                            break;
                        case 4:
                            tempButton = radio4;
                            break;
                        case 5:
                            tempButton = radio5;
                            break;
                    }

                    String tempKey = keys.next();
                    String tempVal = obj.getString(tempKey);
                    if (tempKey.equals("Number")) {
                        partyId = Integer.parseInt(tempVal);
                    } else if (tempKey.equals("Name")) {
                        partyName = tempVal;
                    } else if (tempKey.equals("Available")) {
                        spotsLeft = Integer.parseInt(tempVal);
                    }
                    tempButton.setTextKeepState(tempButton.getText() + "\n"
                                                + tempKey + ": " + tempVal);

                }
                idRoomLeftMap.put(tempButton, spotsLeft);
                idNameMap.put(tempButton, partyName);
                Log.d("WebSocket", message);
                startActivity(new Intent(getApplicationContext(), PlayerIDActivity.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {
        Log.d("error", ex.toString());
    }
}