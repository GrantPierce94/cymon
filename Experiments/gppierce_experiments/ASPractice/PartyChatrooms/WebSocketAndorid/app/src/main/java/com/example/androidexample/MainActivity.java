package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import org.java_websocket.handshake.ServerHandshake;

public class MainActivity extends AppCompatActivity implements WebSocketListener {

    private String BASE_URL = "ws://10.0.2.2:8080/chat/";

    private Button connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;
    private Spinner roomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = findViewById(R.id.bt1);
        sendBtn = findViewById(R.id.bt2);
        usernameEtx = findViewById(R.id.et1);
        roomSpinner = findViewById(R.id.spinner_room);
        msgEtx = findViewById(R.id.message_input);
        msgTv = findViewById(R.id.chat_messages);

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
            String serverUrl = BASE_URL + selectedRoom + "/" + usernameEtx.getText().toString();
//            String serverUrl = BASE_URL + "/" + usernameEtx.getText().toString();
            Log.d("Url ", serverUrl);
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(MainActivity.this);
        });

        sendBtn.setOnClickListener(v -> {
            try {
                String msg = msgEtx.getText().toString();
                Log.d("Sent: ", msg);
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
            String setMsg = s + "\n" + message;
            msgTv.setText(setMsg);
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