package com.example.battleparty;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Player {
    String playerID, name;
    public Player(){}

    public Player(String playerID,String name){
        this.playerID=playerID;
        this.name=name;
    }
    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\"Player\" [\"ID\"=\""+playerID+"\",\"name\"=\""+name+"\"]";
    }
}


