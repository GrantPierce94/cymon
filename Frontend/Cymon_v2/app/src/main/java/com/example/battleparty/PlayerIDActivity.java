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
import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;
import com.google.gson.Gson;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;
import com.example.cymonbattle.R;

import kotlin.jvm.Synchronized;

public class PlayerIDActivity extends AppCompatActivity implements WebSocketListener {
    Button btnCreate;
    LinearLayout layoutItems;
    JSONObject obj;
    ArrayList<Player> allPlayersList;
    final String BASE_URL = "ws://10.0.2.2:8080/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_idactivity);
        btnCreate = findViewById(R.id.btnCreate);
        layoutItems = findViewById(R.id.layoutItems);
        String data = getIntent().getStringExtra("obj");

        WebSocketManager.getInstance().setWebSocketListener(PlayerIDActivity.this);
//        String currentPlayerName = WebSocketManager.getPlayer().getName();

        WebSocketManager.getInstance().sendMessage("getPlayers");

        int players = 0;
        try {
            obj = new JSONObject(data);
            players = obj.getInt("players");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] editTextIDs = new String[players];
        allPlayersList = new ArrayList<>();

        for (int i = 0; i < players; i++) {
            EditText editText = new EditText(this);
            String id = "player" + String.valueOf(i);
            editTextIDs[i] = id;
            editText.setTag(id);
            editText.setHint("Player " + String.valueOf(i + 1) + " name");
            layoutItems.addView(editText);
        }

        int finalPlayers = players;
        btnCreate.setOnClickListener(new View.OnClickListener() {
            boolean isValidated = true;

            @Override
            public void onClick(View view) {
                allPlayersList.clear(); // Clear the list before adding players
                for (int i = 0; i < finalPlayers; i++) {
                    EditText current = layoutItems.findViewWithTag(editTextIDs[i]);
                    String name = current.getText().toString();

                    if (name.isEmpty()) {
                        isValidated = false;
                    }
                    String uid = UUID.randomUUID().toString();
                    allPlayersList.add(new Player(uid, name));
                }

                if (!isValidated) {
                    Toast.makeText(getApplicationContext(), "Enter the names of all players", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Gson gson = new Gson();
                        JSONArray allPlayersArray = new JSONArray();

                        for (Player player : allPlayersList) {
                            JSONObject playerJson = new JSONObject();
                            playerJson.put("playerID", player.getPlayerID());
                            playerJson.put("name", player.getName());
                            allPlayersArray.put(playerJson);
                        }

                        obj.put("all_players", allPlayersArray);
                        Log.i("battle_party", "JSON Data: " + obj.toString());

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String url = "https://02acd35d-4fc0-48f5-b85d-4c0838446cc7.mock.pstmn.io"; // Replace with your API endpoint URL



//                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                                Request.Method.POST,
//                                url,
//                                obj,
//                                new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        Log.i("battle_party", "Response: " + response.toString());
//                                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Log.e("battle_party", "Volley Error: " + error.toString());
//                                        Toast.makeText(getApplicationContext(), "Failed to send request.", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                        requestQueue.add(jsonObjectRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}