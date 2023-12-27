package com.example.battleparty.WebSockets;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public interface WebSocketListener {

    // when connection is successfully opened
    void onWebSocketOpen(ServerHandshake handshakedata);

    // when websocket message is received
    void onWebSocketMessage(String message);

    // when websocket connection is closed
    void onWebSocketClose(int code, String reason, boolean remote);

    // when error occurs in websockect communications
    void onWebSocketError(Exception ex);

}
