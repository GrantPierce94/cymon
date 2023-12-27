package com.example.signin.Logic;

import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.signin.IView;
import com.example.signin.SignInActivity;
import com.example.signin.Network.ServerRequest;
import com.example.signin.Network.IServerRequest;

public class RegistrationLogic implements IVolleyListener {

    IView r;
    IServerRequest serverRequest;

    public RegistrationLogic(IView r, IServerRequest serverRequest) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    public void registerUser(String name, String email, String password) throws JSONException {
       //TODO: change this url
//        String url = "https://dfe23b05-4ae9-4a3f-9142-b27a616dcd58.mock.pstmn.io";
        String url = "http://10.0.2.2:8080/";
//        String url = "http://10.0.2.2:8080/user";

        JSONObject newUserObj = new JSONObject();
        newUserObj.put("name", name);
        newUserObj.put("email", email);
        newUserObj.put("password", password);

        serverRequest.sendToServer(url, newUserObj, "POST");
    }

    public void removeUser(int id) throws JSONException {
//        String url = "http://10.0.2.2:8080/";
        String url = "http://10.0.2.2:8080/";

        JSONObject newUserObj = new JSONObject();
        newUserObj.put("id", id);

        serverRequest.removeFromServer(url, newUserObj, "DELETE");
    }

    @Override
    public void onSuccess(JSONObject obj) {
        if (obj != null) {
            r.showText(obj.toString());
        }
//        if (email.length() > 0) {
//            // here you can give access to the app
//            //startActivity(new Intent(getApplicationContext(), LoginScreen.class));
//            r.showText("You are logged in");
//        } else {
//            r.showText("Email cannot be empty");
//        }
    }

    @Override
    public void onError(String errorMessage) {
        r.toastText(errorMessage);
        r.showText("Please try again");
    }
}
