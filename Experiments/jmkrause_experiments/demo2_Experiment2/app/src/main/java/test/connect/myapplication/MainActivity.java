package test.connect.myapplication;

import static test.connect.myapplication.api.ApiClientFactory.GetPersonApi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.List;

import test.connect.myapplication.api.SlimCallback;
import test.connect.myapplication.model.Person;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);
        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        GetPersonApi().GetAllPerson().enqueue(new SlimCallback<List<Person>>(person ->{
            apiText1.setText("");

            for (int i = person.size()-1; i>=0; i--) {
                apiText1.append(person.get(i).printable());
            }

        }, "GetAllPerson"));

    }
}

