package com.example.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.signin.Logic.RegistrationLogic;
import com.example.signin.Network.ServerRequest;

public class SignInActivity extends AppCompatActivity implements IView {
    EditText nameTextView;
    EditText emailTextView;
    EditText passwordTextView;
    public TextView registerErrorTextView;
    Button registerSubmitButton;
    Button advanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_main);

        nameTextView = findViewById(R.id.main_editText_name);
        emailTextView = findViewById(R.id.main_editText_email);
        passwordTextView = findViewById(R.id.main_editText_password);
        registerErrorTextView = findViewById(R.id.main_textView_data);
        advanceButton = findViewById(R.id.main_button_advanceScreen);
        registerSubmitButton = findViewById(R.id.main_button_register);

        ServerRequest serverRequest = new ServerRequest();
        final RegistrationLogic logic = new RegistrationLogic(this, serverRequest);

        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RemoveAcctActivity.class));
            }
        });

        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = nameTextView.getText().toString();
                    String email = emailTextView.getText().toString();
                    String password = passwordTextView.getText().toString();
//                    String name = "na";
//                    String email = "em";
//                    String password = "pa";
                    logic.registerUser(name, email, password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    //oncreate()
    }

    @Override
    public void showText(String s) {
        registerErrorTextView.setText(s);
        registerErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void toastText(String s) {
        registerErrorTextView.setText(s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}