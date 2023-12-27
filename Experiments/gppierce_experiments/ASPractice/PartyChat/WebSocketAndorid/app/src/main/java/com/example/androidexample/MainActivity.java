package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements WebSocketListener {
    private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private Button connectBtn, sendBtn; // Now corresponds to bt1 and bt2 in XML
    private EditText usernameEtx, msgEtx; // Corresponds to et1 and et2 in XML
    private TextView msgTv; // Now corresponds to tx1 in XML
    private Spinner roomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize UI elements */
        connectBtn = findViewById(R.id.bt1); // Corresponds to bt1 in XML
        sendBtn = findViewById(R.id.bt2); // Corresponds to bt2 in XML
        usernameEtx = findViewById(R.id.et1);
        msgEtx = findViewById(R.id.et2); // Corresponds to et2 in XML
        msgTv = findViewById(R.id.tx1); // Corresponds to tx1 in XML
        roomSpinner = findViewById(R.id.spinner_room);

        /* Connect button listener */
        connectBtn.setOnClickListener(view -> {
            String serverUrl = BASE_URL + usernameEtx.getText().toString() + "/" + roomSpinner.getSelectedItem().toString();

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);
        });

        /* Send button listener */
        sendBtn.setOnClickListener(v -> {
            try {
                // Send message
                WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n" + message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onWebSocketError(Exception ex) {
    }
}