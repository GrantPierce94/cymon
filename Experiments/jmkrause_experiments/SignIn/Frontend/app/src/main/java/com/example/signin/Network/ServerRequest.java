package com.example.signin.Network;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.example.signin.AppController;
import com.example.signin.Logic.IVolleyListener;
import com.example.signin.Logic.RegistrationLogic;

public class ServerRequest implements IServerRequest {

    public String tag_json_obj = "json_obj_req";
    private IVolleyListener volleyListener;

    @Override
    public void sendToServer(String url, JSONObject newUserObj, String methodType) {
        JsonObjectRequest registerUserRequest = new JsonObjectRequest(Request.Method.POST, url, newUserObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            volleyListener.onSuccess(response);
                            System.out.println(response.toString());
                        } else {
                            volleyListener.onError("Null response object.");
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyListener.onError(error.getMessage());
                    }
                }
                );
        AppController.getInstance().addToRequestQueue(registerUserRequest, tag_json_obj);

    }

    @Override
    public void removeFromServer(String url, JSONObject newUserObj, String methodType) {
        //TODO: implement this
        JsonObjectRequest registerUserRequest = new JsonObjectRequest(Request.Method.POST, url, newUserObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            volleyListener.onSuccess(response);
                            System.out.println(response.toString());
                        } else {
                            volleyListener.onError("Null response object.");
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyListener.onError(error.getMessage());
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(registerUserRequest, tag_json_obj);
    }

    @Override
    public void addVolleyListener(IVolleyListener logic) {
        volleyListener = logic;
    }
}
