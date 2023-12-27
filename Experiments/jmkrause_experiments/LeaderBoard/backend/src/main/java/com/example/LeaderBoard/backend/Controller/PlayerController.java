package com.example.LeaderBoard.backend.Controller;

import com.example.LeaderBoard.backend.Model.Game;
import com.example.LeaderBoard.backend.Model.Player;
import com.example.LeaderBoard.backend.Repository.GameRepository;
import com.example.LeaderBoard.backend.Repository.PlayerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @PostMapping("/")
    String createPlayer(@RequestBody Player player) {
        if (player == null) { return "Null JSON"; }
        Player temp = playerRepository.save(player);
        JSONObject ret = new JSONObject();
        // Add associated games to the new Player
        List<Game> games = player.getGames();
        if (games != null) {
            for (Game game : games) {
                temp.setGamesPlayed(temp.getGamesPlayed()+1);
                game.setPlayer(temp);
                gameRepository.save(game);
            }
            ret.put("Name", player.getName());
            ret.put("Games", games.size());
        }


        return ret.toString();
    }




}
