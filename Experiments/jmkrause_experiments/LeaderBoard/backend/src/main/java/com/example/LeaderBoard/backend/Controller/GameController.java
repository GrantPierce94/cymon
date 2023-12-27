package com.example.LeaderBoard.backend.Controller;

import com.example.LeaderBoard.backend.Model.Game;
import com.example.LeaderBoard.backend.Model.Player;
import com.example.LeaderBoard.backend.Repository.GameRepository;
import com.example.LeaderBoard.backend.Repository.PlayerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {
    //TODO: make both controllers

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

//    @PostMapping("/game")
//    String addSingleGameToPlayer(@RequestBody Game game) {
//        if (game == null) { return "Null JSON"; }
//        Player temp = playerRepository.findById(game.getPId());
//        if (temp == null) { return "Cannot find player"; }
//        game.setPlayer(temp);
//        gameRepository.save(game);
//        JSONObject ret = new JSONObject();
//        int gamesP = temp.getGamesPlayed();
//        temp.setGamesPlayed(gamesP+1);
//        ret.put("player", game.getPlayer().getName());
//        ret.put("total:", gamesP+1);
//        return ret.toString();
//    }

    @PostMapping("/game/{id}")
    String addGame(@PathVariable int id, @RequestBody Game game) {
        Player player = playerRepository.findById(id);
        if (player == null) { return "Failed"; }
        game.setPlayer(player);
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        gameRepository.save(game);
        JSONObject ret = new JSONObject();
        ret.put("player", player.getName());
        ret.put("total", player.getGamesPlayed());
        return ret.toString();
    }

//    @DeleteMapping("/game/remove/{id}")
//    String removeGame(@PathVariable int id, @RequestBody Game game) {
//        Player player = playerRepository.findById(id);
//        if (player == null) { return "Failed Removal"; }
//        Game emp = gameRepository.findById()
//    }


}
