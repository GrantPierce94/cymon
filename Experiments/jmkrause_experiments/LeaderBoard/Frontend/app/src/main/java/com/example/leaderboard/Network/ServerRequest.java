package com.example.leaderboard.Network;

import com.example.leaderboard.AppController;
import com.example.leaderboard.IView;
import com.example.leaderboard.Logic.IVolleyListener;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


public class ServerRequest implements IServerRequest {

    public String tag_json_obj = "json_obj_req";
    private IVolleyListener volleyListener;


    @Override
    public void sendToServer(String url, JSONObject jsonObject, String methodType) {
        int method = Request.Method.POST;
        if (methodType.equals("DELETE")) {
            method = Request.Method.DELETE;
        }
        JsonObjectRequest registerUserRequest = new JsonObjectRequest(method, url, jsonObject,
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
