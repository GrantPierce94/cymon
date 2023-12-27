package com.example.signin.Network;

import com.example.signin.Logic.IVolleyListener;
import org.json.JSONObject;

public interface IServerRequest {
    public void sendToServer(String url, JSONObject newUserObj, String methodType);
    public void removeFromServer(String url, JSONObject newUserObj, String methodType);
    void addVolleyListener(IVolleyListener logic);
}
