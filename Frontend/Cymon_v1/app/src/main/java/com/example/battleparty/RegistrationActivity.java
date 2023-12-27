package com.example.battleparty;

import static com.example.battleparty.SignInActivity.email;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.UtilEnum;
import com.example.battleparty.WebSockets.WebSocketListener;
import com.example.battleparty.WebSockets.WebSocketManager;

public class RegistrationActivity extends AppCompatActivity implements WebSocketListener {

    EditText nameTextView;
    EditText emailTextView;
    EditText passwordTextView;
    public TextView registerErrorTextView;
    Button registerSubmitButton;
    Button advanceButton;
    RequestQueue requestQueue;
    boolean isReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        isReg = false;

        nameTextView = findViewById(R.id.main_editText_name);
        emailTextView = findViewById(R.id.main_editText_email);
        passwordTextView = findViewById(R.id.main_editText_password);
        registerErrorTextView = findViewById(R.id.main_textView_data);
        advanceButton = findViewById(R.id.main_button_advanceScreen);
        registerSubmitButton = findViewById(R.id.main_button_register);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), PlayerRemoval.class));
            }
        });

        //to be called twice - for testing - second click will send user to next activity
        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isReg) {
                    try {
                        String name = nameTextView.getText().toString();
                        String email = emailTextView.getText().toString();
                        String password = passwordTextView.getText().toString();
                        WebSocketManager.getPlayer().setName(email);
                        isReg = true;
                        createUser(name, email, password, UtilEnum.URL.BASE_URL_H.getURL());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(view.getContext(), MainScreen.class));
                    finish();
                }
            }
        });
    }

    public void createUser(String name, String email, String password, String url) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("email", email);
        obj.put("password", password);

        JsonObjectRequest createUserRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        registerErrorTextView.setText(response.toString());
                        String currentPlayerName = WebSocketManager.getPlayer().getName();
                        //connects all websockets
                        WebSocketManager.getInstance().setWebSocketListener(RegistrationActivity.this);
                        WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.FRIEND_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.FRIENDCONTROLLER);
                        WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.ITEM_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.ITEMCONTROLLER);
                        WebSocketManager.getInstance().connectWebSocket(UtilEnum.URL.PARTY_URL.getURL() + currentPlayerName, UtilEnum.CONTROLLER.PARTYCONTROLLER);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        registerErrorTextView.setText(error.toString());
                        System.out.println(error.toString());
                    }
                });
        requestQueue.add(createUserRequest);
    }


    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onWebSocketMessage(String message) {
        Log.d("[Registration message] ", message);
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        Log.d("[webS close] ", reason);
    }

    @Override
    public void onWebSocketError(Exception ex) {
        ex.printStackTrace();
    }
}
