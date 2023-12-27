package SignIn.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SignIn.backend.Model.Player;
import SignIn.backend.Model.Game;
import SignIn.backend.Repository.PlayerRepository;
import SignIn.backend.Repository.GameRepository;
import org.json.JSONObject;


import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;



    @PostMapping("/player")
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
