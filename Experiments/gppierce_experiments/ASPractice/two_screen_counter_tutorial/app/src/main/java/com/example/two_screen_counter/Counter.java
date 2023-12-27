package com.example.two_screen_counter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Counter extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);

        TextView num = findViewById(R.id.counter_textView_count);
        Button increment = findViewById(R.id.counter_button_increment);
        Button decrement = findViewById(R.id.counter_button_decrement);
        Button home = findViewById(R.id.counter_button_home);
        Integer[] count = {0};

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                num.setText(count[0].toString());
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]--;
                num.setText(count[0].toString());
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

    }


}

