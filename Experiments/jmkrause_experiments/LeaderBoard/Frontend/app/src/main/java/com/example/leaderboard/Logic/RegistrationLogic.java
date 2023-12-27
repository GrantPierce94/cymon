package com.example.leaderboard.Logic;

import com.example.leaderboard.IView;
import com.example.leaderboard.Network.IServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationLogic implements IVolleyListener {
    IView r;
    IServerRequest serverRequest;

    public RegistrationLogic(IView r, IServerRequest serverRequest) {
        this.r = r;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

//TODO: implement this
    public void registerGameAndPlayer(String name, ArrayList<Integer> points) throws JSONException {
        //TODO: change url
        String url = "http://10.0.2.2:8080/";
//        String url = "https://ab131aa9-24e1-4af8-871a-1f955aaf0e4c.mock.pstmn.io";

        JSONObject player = new JSONObject();
        player.put("name", name);
        JSONArray games = new JSONArray();
//        JSONObject games = new JSONObject();
        for (Integer point : points) {
//            games.put("score", point);
            JSONObject temp = new JSONObject();
            temp.put("score", point);
            games.put(temp);
        }
        player.put("games", games);
//        System.out.println(player.toString());
//        System.out.println(games.toString());
        serverRequest.sendToServer(url, player, "POST");
    }

    public void registerGame(int id, int point) throws JSONException {
        //TODO: change url
        String url = "http://10.0.2.2:8080/";
//        String url = "https://ab131aa9-24e1-4af8-871a-1f955aaf0e4c.mock.pstmn.io";
        StringBuilder s = new StringBuilder(url);
        s.append("game/");
        s.append(id);

        JSONObject game = new JSONObject();
        game.put("score", point);
        serverRequest.sendToServer(s.toString(), game, "POST");
    }



    @Override
    public void onSuccess(JSONObject obj) {
        if (obj != null) {
            r.showText(obj.toString());
        }
    }

    @Override
    public void onError(String s) {
        r.toastText(s);
        r.showText("Please try again");
    }
}
