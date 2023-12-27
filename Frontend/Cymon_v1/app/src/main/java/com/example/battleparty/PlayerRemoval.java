package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.UtilEnum;

import org.json.JSONException;
import org.json.JSONObject;


public class PlayerRemoval extends AppCompatActivity {

    Button removeButton;
    TextView dataToShow;
    EditText userId;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_removal);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        removeButton = findViewById(R.id.remove_button_delete);
        dataToShow = findViewById(R.id.remove_textView_data);
        userId = findViewById(R.id.remove_editText_id);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!TextUtils.isEmpty(userId.getText().toString())) {
                        int id = Integer.parseInt(userId.getText().toString());
                        String url = UtilEnum.URL.BASE_URL_H + "delete/" + id;
                        JSONObject obj = new JSONObject();
                        obj.put("id", id);
                        StringRequest deleteUserRequest = new StringRequest(
                                Request.Method.DELETE,
                                url,
                                new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                        System.out.println(response.toString());
                                dataToShow.setText(response.toString());
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        dataToShow.setText(error.toString());
//                        System.out.println(error.toString());
                                    }
                                });
                        requestQueue.add(deleteUserRequest);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });





    }

}
