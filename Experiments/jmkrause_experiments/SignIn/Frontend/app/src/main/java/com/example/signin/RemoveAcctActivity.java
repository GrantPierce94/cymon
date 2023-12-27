package com.example.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.signin.Logic.RegistrationLogic;
import com.example.signin.Network.ServerRequest;

import org.json.JSONException;

public class RemoveAcctActivity extends AppCompatActivity implements IView {
    Button removeButton;
    TextView dataToShow;
    EditText userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppController();
        setContentView(R.layout.activity_remove_acct);

        removeButton = findViewById(R.id.remove_button_delete);
        dataToShow = findViewById(R.id.remove_textView_data);
        userId = findViewById(R.id.remove_editText_id);

        ServerRequest removeServerRequest = new ServerRequest();
        final RegistrationLogic removeLogic = new RegistrationLogic(this, removeServerRequest);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int id = Integer.parseInt(userId.getText().toString());
                    removeLogic.removeUser(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    @Override
    public void showText(String s) {
        dataToShow.setText(s);
        dataToShow.setVisibility(View.VISIBLE);
    }

    @Override
    public void toastText(String s) {

    }
}