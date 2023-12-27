package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.battleparty.WebSockets.WebSocketListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.WebSocketManager;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.cymonbattle.R;

public class Store extends AppCompatActivity implements WebSocketListener {
    final String URL = "http://10.0.2.2:8080/item/";
    String playerEmail = WebSocketManager.getPlayer().getName();

    TextView coinsText;

    Button item1Button;
    TextView item1Title;
    TextView item1Effect;

    Button item2Button;
    TextView item2Title;
    TextView item2Effect;

    Button item3Button;
    TextView item3Title;
    TextView item3Effect;

    Button item4Button;
    TextView item4Title;
    TextView item4Effect;

    Button item5Button;
    TextView item5Title;
    TextView item5Effect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        WebSocketManager.getInstance().setWebSocketListener(Store.this);


        coinsText = findViewById(R.id.storeCoinsRemaining);

        item1Button = findViewById(R.id.btnItem1);
        item2Button = findViewById(R.id.btnItem2);
        item3Button = findViewById(R.id.btnItem3);
        item4Button = findViewById(R.id.btnItem4);
        item5Button = findViewById(R.id.btnItem5);


        item1Effect = findViewById(R.id.item1Effect);
        item2Effect = findViewById(R.id.item2Effect);
        item3Effect = findViewById(R.id.item3Effect);
        item4Effect = findViewById(R.id.item4Effect);
        item5Effect = findViewById(R.id.item5Effect);

        item1Title = findViewById(R.id.item1Title);
        item2Title = findViewById(R.id.item2Title);
        item3Title = findViewById(R.id.item3Title);
        item4Title = findViewById(R.id.item4Title);
        item5Title = findViewById(R.id.item5Title);
        
        item1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = item1Title.getText().toString();
                String effect = item1Effect.getText().toString();
                try {
                    purchaseItem(playerEmail, title, effect, 10);
                    updateBalance(3);
                } catch (Exception e) {
                    Log.d("[Error purchasing] ", e.getMessage());
                }

            }
        });

        item2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = item2Title.getText().toString();
                String effect = item2Effect.getText().toString();
                try {
                    purchaseItem(playerEmail, title, effect, 15);
                    updateBalance(5);
                } catch (Exception e) {
                    Log.d("[Error purchasing] ", e.getMessage());
                }

            }
        });

        item3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = item3Title.getText().toString();
                String effect = item3Effect.getText().toString();
                try {
                    purchaseItem(playerEmail, title, effect, 20);
                    updateBalance(7);
                } catch (Exception e) {
                    Log.d("[Error purchasing] ", e.getMessage());
                }

            }
        });

        item4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = item4Title.getText().toString();
                String effect = item4Effect.getText().toString();
                try {
                    purchaseItem(playerEmail, title, effect, 25);
                    updateBalance(9);
                } catch (Exception e) {
                    Log.d("[Error purchasing] ", e.getMessage());
                }

            }
        });

        item5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = item5Title.getText().toString();
                String effect = item5Effect.getText().toString();
                try {
                    purchaseItem(playerEmail, title, effect, 30);
                    updateBalance(11);
                } catch (Exception e) {
                    Log.d("[Error purchasing] ", e.getMessage());
                }

            }
        });

    }

    public void purchaseItem(String playerEmail, String itemName, String effect, int magnitude) throws JSONException {
        String urlToSend = URL + playerEmail + '/';
        JSONObject obj = new JSONObject();
        obj.put("name", itemName);
        obj.put("effect", effect);
        obj.put("magnitude", magnitude);

        JsonObjectRequest itemRequest = new JsonObjectRequest(Request.Method.POST, urlToSend, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                updateBalance(10);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("[Error purchaseItem] ", error.getMessage());
            }
        });
    }

    public void updateBalance(int n) {
        WebSocketManager.getInstance().sendMessage(String.valueOf(n));
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        try {
            int n = Integer.parseInt(message);
            int balance = Integer.parseInt(coinsText.getText().toString());
            coinsText.setText(balance);
        } catch (Exception e) {
            Log.d("[onMessage] ", e.getMessage());
        }
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}