package com.example.battleparty;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import com.example.battleparty.WebSockets.WebSocketListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PartyChat extends AppCompatActivity implements WebSocketListener {

    private Button connectBtn, sendBtn, backToMainBtn; // Add back button
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;
    private Spinner roomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partychat);

        connectBtn = findViewById(R.id.bt1);
        sendBtn = findViewById(R.id.bt2);
        usernameEtx = findViewById(R.id.et1);
        roomSpinner = findViewById(R.id.spinner_room);
        msgEtx = findViewById(R.id.message_input);
        msgTv = findViewById(R.id.chat_messages);
        backToMainBtn = findViewById(R.id.bt3); // Initialize back button

        List<String> roomList = new ArrayList<>();
        roomList.add("BadgeBandits");
        roomList.add("GymGladiators");
        roomList.add("RocketRebels");
        roomList.add("CymonScholars");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(spinnerAdapter);

        connectBtn.setOnClickListener(view -> {
            String selectedRoom = roomSpinner.getSelectedItem().toString();


        });

        sendBtn.setOnClickListener(v -> {
            try {
                String msg = msgEtx.getText().toString();
                Log.d("Sent: ", msg);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add an OnClickListener for the back button
        backToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate back to the MainScreen
                Intent intent = new Intent(PartyChat.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d("WebSocket", "Connected");
    }

    @Override
    public void onWebSocketMessage(String message) {
        Log.d("WebSocket", "Received message: " + message);
        onWebSocketMessage(message);
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        Log.d("WebSocket", "Closed");
    }

    @Override
    public void onWebSocketError(Exception ex) {
        Log.d("WebSocket", "Error");
        onWebSocketError(ex);
    }
}

