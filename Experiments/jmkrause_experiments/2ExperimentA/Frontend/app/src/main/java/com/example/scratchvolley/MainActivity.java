package com.example.scratchvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scratchvolley.controller.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        EditText user = findViewById(R.id.main_editText_user);
        EditText pass = findViewById(R.id.main_editText_pass);
        Button enter = findViewById(R.id.main_button_enter);
        Button post = findViewById(R.id.main_button_post);
        TextView info = findViewById(R.id.main_editText_info);

        String url = "https://b7eee402-d7c2-4577-9c20-099fe9aa6c37.mock.pstmn.io";

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create JSON object with keys 'user' and 'password'
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("user", "userHardCode");
                    jsonBody.put("pass", "passHardCode");
//                    jsonBody.put("user", user.getText().toString());
//                    jsonBody.put("pass", pass.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                String username = user.getText().toString();
                String username = "username";
//                String password = pass.getText().toString();
                String password = "password";


                JsonObjectRequest jsonRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String responseData = response.toString();
                                info.setText(responseData);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                info.setText("err");
                            }
                        });
                requestQueue.add(jsonRequest);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            //TODO: set info from response
                            @Override
                            public void onResponse(String response) {
                                info.setText(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                info.setText("Error");
                            }
                        }
                ) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        String toSend = user.getText().toString() + pass.getText().toString();
                        return toSend.getBytes();
                    }
                };
//                requestQueue.add(stringRequest, toSend);
                requestQueue.add(stringRequest);

            }
        });


    }
}