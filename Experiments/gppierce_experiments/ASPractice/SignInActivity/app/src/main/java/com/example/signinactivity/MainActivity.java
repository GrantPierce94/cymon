package com.example.signinactivity;
//This Screen is the Login Screen for our App and 1st round trip
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.user_manager_v1.helpers.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button sign_in_btn;
    EditText et_email, et_password;
    public static String email = "";

    //public static String useridFindMe;
    //Manager and candidate login fix screen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hook Edit Text Fields:
        et_email = findViewById(R.id.email);
        //useridFindMe = et_email.getText().toString();
        et_password = findViewById(R.id.password);
        email = et_email.getText().toString();
        Log.d("SignInActivity","look at this"+email);

        // Hook Button:
        sign_in_btn = findViewById(R.id.sign_in_btn);

        // Set Sign In Button On Click Listener:
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // authenticateUser();

                authenticateUser();

                String email = et_email.getText().toString();
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("email",email);
                editor.apply();
            }
        });
    }
    // End Of On Create Activity.

    public void authenticateUser(){
        // Check For Errors:
        if( !validateEmail() || !validatePassword()){
            return;
        }
        // End Of Check For Errors.

        // Instantiate The Request Queue:
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // The URL Posting TO:
        String url = "https://f759a971-21f8-46be-a1ab-d2eef799e545.mock.pstmn.io";
//        String url = "http://coms-309-318.class.las.iastate.edu:8080/users/login";// make change here according to backend post-mapping
//      String url = "https://c22f9a6f-f2f0-450c-b028-77ba9d269646.mock.pstmn.io"; //postman request url
//        // Set Parameters:
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("userid", et_email.getText().toString());
//        params.put("password", et_password.getText().toString());
        JSONObject jsonRequest = new JSONObject();
        try {
            //jsonRequest.put("id", 0);
            jsonRequest.put("email", et_email.getText().toString()); //was email changed from userid could be anything
            jsonRequest.put("password", et_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set Request Object:
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                boolean result = Boolean.parseBoolean(response);
                if(response.equals("false")){
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                String[] splitStrings = response.split(",");
                String access = splitStrings[0];

                String type = splitStrings[1];
                if(access.equals("true")) {

                    String email = et_email.getText().toString();
                    SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("email",email);
                    editor.apply();
                    if(type.equals("1")) {
                        Intent goToProfile = new Intent(MainActivity.this, MainActivity.class);
                        System.out.println(email);
                        et_email.setText("Good to go");
                        finish();
                    }

                    if(type.equals("2")){
                        Intent goToProfile = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(goToProfile);
                        System.out.println("Not working");
                        finish();
                    }
                }
                // Handle the boolean response here
                //Change the classes to interact with other Java files in future project
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error here
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_LONG).show(); //displays are error for easy troubleshooting
            }
        }){
            @Override
            public byte[] getBody() {
                return jsonRequest.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(request);
    }

/*  These are two Methods to take you to Home Screen or to SignUp Screen
    public void goToHome(View view){
        Intent intent = new Intent(SignInActivity.this, ReturnHome.class);
        startActivity(intent);
        finish();
    }
    // End Of Go To Home Intent Method.


    public void goToSigUpAct(View view){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    // End Of Go To Sign Up Intent Method.
*/

    public boolean validateEmail(){
        String email = et_email.getText().toString();
        // Check If Email Is Empty:
        if(email.isEmpty()){
            et_email.setError("Email cannot be empty!");
            return false;
        }//else if(!StringHelper.regexEmailValidationPattern(email)){
        //et_email.setError("Please enter a valid email");
        //    return false;
        //}
        else{
//            et_email.setText("Empty");
            return true;
        }// Check If Email Is Empty.
    }
    // End Of Validate Email Field.

    public boolean validatePassword() {
        String password = et_password.getText().toString();

        // Check If Password and Confirm Field Is Empty:
        if (password.isEmpty()) {
            et_password.setError("Password cannot be empty!");
            return false;
        } else {
//            et_password.setError("Empty");
            return true;
        }// Check Password and Confirm Field Is Empty.
    }
    // End Of Validate Password;


}
// End Of Sign In Activity Class.