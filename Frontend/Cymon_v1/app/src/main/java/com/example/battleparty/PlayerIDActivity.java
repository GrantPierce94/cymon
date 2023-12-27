package com.example.battleparty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.UtilEnum;
import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;
import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import kotlin.jvm.Synchronized;

public class PlayerIDActivity extends AppCompatActivity implements WebSocketListener {
    Button btnCreate;
    LinearLayout layoutItems;
    JSONObject obj;
    ArrayList<Player> allPlayersList;
    String inPartyStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_idactivity);
        btnCreate = findViewById(R.id.btnCreate);
        layoutItems = findViewById(R.id.layoutItems);
        String data = getIntent().getStringExtra("obj");

        //string for testing purposes
        inPartyStr = "";

        WebSocketManager.getInstance().setWebSocketListener(PlayerIDActivity.this);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebSocketManager.getInstance().sendMessage("getPlayers", UtilEnum.CONTROLLER.PARTYCONTROLLER);
            }
        });

    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            try {
                JSONArray arr = new JSONArray(message);
                for (int i = 0; i < arr.length(); i++) {
                    String s = arr.getString(i);
                    EditText tempEditText = new EditText(this);
                    tempEditText.setText(s);
                    layoutItems.addView(tempEditText);
                    inPartyStr = s;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public String getTestingString() {
        return inPartyStr;
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}