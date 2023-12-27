package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.battleparty.WebSockets.UtilEnum;

import org.json.JSONException;
import org.json.JSONObject;


public class EditScores extends AppCompatActivity {

    Button addScore;
    EditText id;
    EditText score;
    TextView data;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scores);


        addScore = findViewById(R.id.editScores_button_add);
        id = findViewById(R.id.editScores_editText_id);
        score = findViewById(R.id.editScores_editText_score);
        data = findViewById(R.id.editScores_textView_data);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int playerId = Integer.parseInt(id.getText().toString());
                    int gameScore = Integer.parseInt(score.getText().toString());
                    registerGame(playerId, gameScore);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }//oncreate

    public void registerGame(int id, int  point) throws JSONException {
        String url = UtilEnum.URL.BASE_URL_H + "game/" + id;
        JSONObject game = new JSONObject();
        game.put("score", point);

        JsonObjectRequest createGameRequest = new JsonObjectRequest(Request.Method.POST, url, game,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        data.setText(error.toString());
//                        System.out.println(error.toString());
                    }
                });
        requestQueue.add(createGameRequest);
    }

}
