package SignIn.backend.Controller;

import SignIn.backend.Model.Moves;
import SignIn.backend.Repository.MovesRepository;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import SignIn.backend.Repository.GameRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


import SignIn.backend.Model.Player;
import SignIn.backend.Model.Game;
import SignIn.backend.Repository.PlayerRepository;

import java.util.*;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MovesRepository movesRepository;

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * @param id id for player to add game to
     * @param game game to add
     * @return total number of games played by player
     */
//not really needed anymore
    @PostMapping("/game/{id}")
    String addGame(@PathVariable int id, @RequestBody Game game) {
        Player player = playerRepository.findById(id);
        if (player == null) { return null; }
        game.setPlayer(player);
        player.addGamePlayed();
        gameRepository.save(game);
        JSONObject ret = new JSONObject();
        ret.put("player", player.getName());
        ret.put("total", player.getGamesPlayed());
        return ret.toString();
    }


    /**
     * @return returns table of scores for games
     */
    @GetMapping("game/scores")
    Hashtable<String, Integer> getScores() {
//        List<Game> games = gameRepository.findAll(Sort.by("score"));
        List<Game> games = gameRepository.findAll();

        Hashtable<String, Integer> ret = new Hashtable<>();
        for (Game g : games) {
            String playerName = g.getPlayer().getName();
            int playerScore = g.getScore();

            if (ret.containsKey("playerName")) {
                if (playerScore > ret.get(playerName)) {
                    ret.put(playerName, playerScore);
                }
            } else {
                ret.put(playerName, playerScore);
            }

            ret.put(g.getPlayer().getName(), g.getScore());
        }
        return ret;
    }


}
