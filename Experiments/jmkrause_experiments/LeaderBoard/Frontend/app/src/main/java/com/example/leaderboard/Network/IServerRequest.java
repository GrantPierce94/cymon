package com.example.leaderboard.Network;

import com.example.leaderboard.Logic.IVolleyListener;

import org.json.JSONObject;

public interface IServerRequest {

    public void sendToServer(String url, JSONObject jsonObject, String methodType);

    //    public void removeFromServer(String url, JSONObject jsonObject, String methodType);
    void addVolleyListener(IVolleyListener logic);
}
