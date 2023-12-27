package com.example.battleparty;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cymonbattle.R;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class PartyChat extends AppCompatActivity {

    private String BASE_URL = "ws://10.0.2.2:8080/chat/";
    private WebSocketClient webSocketClient;

    private Button connectBtn, sendBtn, mainScreenBtn; // Added mainScreenBtn
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;
    private TextView msgTv2;
    private TextView msgTv3;
    private TextView msgTv4;
    private Spinner roomSpinner;
    private String currentRoom = ""; // Track the currently selected room
    private TextView currentText;
    private ScrollView currentTextContainer;
    private ScrollView container1;
    private ScrollView container2;
    private ScrollView container3;
    private ScrollView container4;

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
        msgTv2 = findViewById(R.id.chat_messages2);
        msgTv3 = findViewById(R.id.chat_messages3);
        msgTv4 = findViewById(R.id.chat_messages4);
        mainScreenBtn = findViewById(R.id.bt3); // Initialize mainScreenBtn
        container1 = findViewById(R.id.scrollView1);
        container2 = findViewById(R.id.scrollView2);
        container3 = findViewById(R.id.scrollView3);
        container4 = findViewById(R.id.scrollView4);
        currentText = msgTv;
        currentTextContainer = container1;

        // List of rooms
        List<String> roomList = new ArrayList<>();
        roomList.add("BadgeBandits");
        roomList.add("GymGladiators");
        roomList.add("RocketRebels");
        roomList.add("CymonScholars");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(spinnerAdapter);

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedRoom = adapterView.getItemAtPosition(position).toString();
                if (!selectedRoom.equals(currentRoom)) {
                    switchRoom(selectedRoom);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });

        connectBtn.setOnClickListener(view -> {
            String selectedRoom = roomSpinner.getSelectedItem().toString();
            String serverUrl = BASE_URL + selectedRoom + "/" + usernameEtx.getText().toString();
            Log.d("Url ", serverUrl);

            try {
                connectWebSocket(serverUrl);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        sendBtn.setOnClickListener(v -> {
            try {
                String msg = msgEtx.getText().toString();
                Log.d("Sent: ", msg);

                if (webSocketClient != null) {
                    webSocketClient.send(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Set OnClickListener for "Main Screen" button
        mainScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainScreen activity when "Main Screen" button is clicked
                Intent intent = new Intent(PartyChat.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void switchRoom(String selectedRoom) {
        Log.d("[Switch]", selectedRoom);
        String msg = "[switch]:" + selectedRoom;
        if (webSocketClient != null) {
            webSocketClient.send(msg);
        }
        currentText.setVisibility(View.GONE);
        currentTextContainer.setVisibility(View.GONE);

        if (selectedRoom.equals("BadgeBandits")) {
            currentText = msgTv;
            currentTextContainer = container1;
        } else if (selectedRoom.equals("GymGladiators")) {
            currentText = msgTv2;
            currentTextContainer = container2;
        } else if (selectedRoom.equals("RocketRebels")) {
            currentText = msgTv3;
            currentTextContainer = container3;
        } else if (selectedRoom.equals("CymonScholars")) {
            currentText = msgTv4;
            currentTextContainer = container4;
        } else {
            Toast.makeText(getApplicationContext(), "Error showing view", Toast.LENGTH_LONG).show();
        }

        currentTextContainer.setVisibility(View.VISIBLE);
        currentText.setVisibility(View.VISIBLE);
    }

    private void connectWebSocket(String serverUrl) throws URISyntaxException {
        URI serverUri = new URI(serverUrl);

        webSocketClient = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("WebSocket", "Connected");
            }

            @Override
            public void onMessage(String message) {
                Log.d("WebSocket", "Received message: " + message);
                onWebSocketMessage(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                String closedBy = remote ? "server" : "local";
                Log.d("WebSocket", "Closed");
                onWebSocketClose(code, reason, closedBy);
            }

            @Override
            public void onError(Exception ex) {
                Log.d("WebSocket", "Error");
                onWebSocketError(ex);
            }
        };

        webSocketClient.connect();
    }

    /**
     * There is no efficient way to do this that were working.
     * @param message
     */
    private void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            Log.d("[onMsg]", message);

            String temp = currentText.getText().toString();
            String str = temp + "\n" + message;
            currentText.setText( str );

        });
    }

    private void onWebSocketClose(int code, String reason, String closedBy) {
        runOnUiThread(() -> {
        });
    }

    private void onWebSocketError(Exception ex) {
        ex.printStackTrace();
    }
}



