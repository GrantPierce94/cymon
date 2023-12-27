package com.example.battleparty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button button2,button4,button8,button16,btnBack,btnContinue;
    EditText txtPartyName;
    private int players=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2=findViewById(R.id.button2);
        button4=findViewById(R.id.button4);
        button8=findViewById(R.id.button8);
        button16=findViewById(R.id.button16);
        btnBack=findViewById(R.id.btnBack);
        btnContinue=findViewById(R.id.btnContinue);
        txtPartyName=findViewById(R.id.txtPartyName);

        button2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                players=2;
                button2.setBackgroundColor(R.color.gray);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                players=4;
                button4.setBackgroundColor(R.color.gray);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                players=8;
                button8.setBackgroundColor(R.color.gray);
            }
        });
        button16.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                players=16;
                button16.setBackgroundColor(R.color.gray);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String partyName=txtPartyName.getText().toString();
                if(partyName.length()==0){
                    Toast.makeText(getApplicationContext(),"You have not entered the party name",Toast.LENGTH_LONG).show();
                }else if(players==0){
                    Toast.makeText(getApplicationContext(),"You have not selected the number of players",Toast.LENGTH_LONG).show();
                }else{
                    JSONObject data=new JSONObject();
                    try {
                        data.put("party_name",partyName);
                        data.put("players",players);
                        String dataInJson=data.toString();
                        Toast.makeText(getApplicationContext(),"Data to be sent \n"+dataInJson,Toast.LENGTH_LONG).show();
                        new DataPoster().execute(dataInJson);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
/*  This is where you can link the back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BackActivity.class));
            }
        });
*/


    }

    class DataPoster extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                // This is the url you should change to your host
                URL url = new URL("https://yourhoust/api/party");

                HttpURLConnection client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("POST");

                client.setRequestProperty("Content-Type", "application/json");
                client.setRequestProperty("Accept", "application/json");

                client.setDoOutput(true);

                try (OutputStream os = client.getOutputStream()) {
                    byte[] input = strings[0].getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(client.getInputStream(), "utf-8"))) {

                    StringBuilder response = new StringBuilder();

                    String responseLine = null;

                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    showToast("Data has been posted to the API.");
                }

            } catch (Exception e) {

                e.printStackTrace();
                showToast("Fail to post the data : " + e.getMessage());
            }
            return null;
        }
        private void showToast(final String message) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}