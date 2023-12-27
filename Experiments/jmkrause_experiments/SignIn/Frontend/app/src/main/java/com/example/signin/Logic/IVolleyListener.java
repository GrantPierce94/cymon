package com.example.signin.Logic;

import org.json.JSONObject;

public interface IVolleyListener {
    public void onSuccess(JSONObject obj);
    public void onError(String s);
}
