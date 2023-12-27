package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LeaderBoard extends AppCompatActivity {

    Button enterScore;
    Button showScores;
    Button go;
    Button advanceToNxt;
    EditText score;
    EditText name;
    TextView dataContainer;
    ArrayList<Integer> scores;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        showScores = findViewById(R.id.main_button_showScores);
        enterScore = findViewById(R.id.main_button_enterScore);
        go = findViewById(R.id.main_button_go);
        score = findViewById(R.id.main_editText_score);
        name = findViewById(R.id.main_editText_name);
        scores = new ArrayList<Integer>();
        advanceToNxt = findViewById(R.id.main_button_advanceToNxt);

        //This allows the scoreboard to be scrollable:
        dataContainer = findViewById(R.id.scoreBoard_data);
        dataContainer.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        enterScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer inputScore = Integer.parseInt(score.getText().toString());
                    scores.add(inputScore);
                    System.out.println(score.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        showScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getStats();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                if (!TextUtils.isEmpty(nameStr) && scores != null) {
                    try {
                        createGameAndPlayer(nameStr, scores);
                        scores = new ArrayList<Integer>();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        advanceToNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), EditScores.class));
            }
        });


    }//oncreate

    public void createGameAndPlayer(String name, ArrayList<Integer> points) throws JSONException {
        JSONObject player = new JSONObject();
        player.put("name", name);
        JSONArray games = new JSONArray();
        for (Integer point : points) {
            JSONObject temp = new JSONObject();
            temp.put("score", point);
            games.put(temp);
        }
        player.put("games", games);
        JsonObjectRequest createPlayerRequest = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2:8080/player", player,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println(response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                        System.out.println(error.toString());
                    }
                });
        requestQueue.add(createPlayerRequest);
    }

    public void getStats() throws JSONException {
        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2:8080/scores", null,
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, "https://2fd0d909-3719-4b0c-838a-b1e210f43a50.mock.pstmn.io/scores", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
                        try {
                            Iterator<String> keys = response.keys();
                            while (keys.hasNext()) {
                                String tempKey = keys.next();
                                int tempScore = response.getInt(tempKey);
                                scoreMap.put(tempKey, tempScore);
                                dataContainer.append(tempKey + tempScore +"\n");
                                System.out.println(tempKey + " " + tempScore);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(arrayRequest);
    }




}
