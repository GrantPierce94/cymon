package com.example.battleparty;

import static com.example.battleparty.WebSockets.WebSocketManager.getPlayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.UtilEnum;
import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity implements WebSocketListener {

    private boolean validated;
    Button sign_in_btn;
    Button register_btn;
    EditText et_email, et_password;
    public static String email = "";
//    final String url = "http://10.0.2.2:8080/";
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        validated = false;
        queue = Volley.newRequestQueue(SignInActivity.this);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);

        // Hook Button:
        sign_in_btn = findViewById(R.id.sign_in_btn);

        // Register Button:
        register_btn = findViewById(R.id.register_btn);

        // Set Sign In Button On Click Listener:
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        // Add Register button On Click Listener:
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RegistrationActivity.class));
            }
        });
    }


    public void authenticateUser() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }



        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("email", et_email.getText().toString());
            jsonRequest.put("password", et_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UtilEnum.URL.BASE_URL_H.getURL() + "signin", jsonRequest,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJson) {
                String response = "";
                Log.d("Message", responseJson.toString());
                Log.d("Sent: ", jsonRequest.toString());
                try {
                    response = responseJson.getString("access");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response.contains("false")) {
                    Toast.makeText(SignInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                } else {
                    String[] splitStrings = response.split(",");
                    String access = splitStrings[0];
                    String type = splitStrings[1];
                    if (access.equals("true")) {
                        validated = true;
                        String email = et_email.getText().toString();
                        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.putString("email", email);
                        editor.apply();
                        WebSocketManager.getInstance().setWebSocketListener(SignInActivity.this);
                        WebSocketManager.getPlayer().setName(email);


                        if (type.equals("1")) {
                            // Navigate to MainScreen.java
                            WebSocketManager.getInstance().setWebSocketListener(SignInActivity.this);
                            String currentPlayerName = WebSocketManager.getPlayer().getName();
                            Intent goToMainScreen = new Intent(SignInActivity.this, MainScreen.class);
                            WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.FRIEND_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.FRIENDCONTROLLER);
                            WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.ITEM_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.ITEMCONTROLLER);
                            WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.PARTY_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.PARTYCONTROLLER);
                            startActivity(goToMainScreen);
                            finish();
                        } else if (type.equals("2")) {
                            // Handle the case for type 2 if needed
                            // You can add your logic here
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignInActivity.this, "Login Error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() {
                return jsonRequest.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(request);
    }

    public boolean validateEmail() {
        String email = et_email.getText().toString();
        if (email.isEmpty()) {
            et_email.setError("Email cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword() {
        String password = et_password.getText().toString();
        if (password.isEmpty()) {
            et_password.setError("Password cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }

    public boolean isValidated() { return validated; }
}
