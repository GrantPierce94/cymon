package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText email = findViewById(R.id.signIn_email);
        EditText password = findViewById(R.id.sign_in_password);
        Button enter = findViewById(R.id.signIn_button_enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCreds(email, password)) {
                    startActivity(new Intent(view.getContext(), TeamScreen.class));
                } else {
                    Toast.makeText(view.getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkCreds(EditText e, EditText p) {
        String email = e.getText().toString();
        String pass = p.getText().toString();
        if (email.equals("") || pass.equals("")) {
            return false;
        } else if (email.equals("user") && pass.equals("password")) {
            return true;
        }
        return false;
    }

}