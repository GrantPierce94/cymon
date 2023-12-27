package com.example.LeaderBoard.backend.Model;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Game> games = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int gamesPlayed;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int n) {
        this.gamesPlayed = n;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
        game.setPlayer(this);
    }
}
