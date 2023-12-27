package SignIn.backend.Controller;

import SignIn.backend.Repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SignIn.backend.Model.Player;
import SignIn.backend.Model.Game;
import SignIn.backend.Repository.PlayerRepository;
import SignIn.backend.Repository.GameRepository;
import org.json.JSONObject;


import java.util.Iterator;
import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @PostMapping("/signin")
    String authenticateUser(@RequestBody String credsStr) {
        JSONObject ret = new JSONObject();
        String email = null;
        String password = null;
        try {
            JSONObject creds = new JSONObject(credsStr);
            logger.info("[json]: " + creds.toString());
            email = creds.getString("email");
            password = creds.getString("password");

            if (email==null || password==null) {
                logger.info("[authenticateUser] " + "null user or pass");
                ret.put("access", "false,2");
                return ret.toString();
            }

            boolean emailTrue = playerRepository.existsByEmail(email);
            boolean passwordTrue = playerRepository.existsByPassword(password);

            if (emailTrue && passwordTrue) {
                ret.put("access", "true,1");
            } else {
                ret.put("access", "false,2");
            }

        } catch (Exception e) {
            ret.put("access", "false,2");
            logger.info("[authenticateUser] " + e.getMessage());
        }

        logger.info("[authenticateUser] " + ret.toString());
        return ret.toString();
    }

    /**
     * @param player player name
     * @return returns how the player has been added to the leaderboard
     */
    @PostMapping("/player")
    String createPlayer(@RequestBody Player player) {
        JSONObject ret = new JSONObject();
        if (player != null) {
            try {
                Player temp = playerRepository.findByName(player.getName());
                List<Game> games = player.getGames();
                if (games != null) {
                    for (Game game : games) {
                        temp.addGamePlayed();
                        game.setPlayer(temp);
                        gameRepository.save(game);
                    }
                    ret.put("Name", player.getName());
                    ret.put("Games", games.size());
                }
            } catch (Exception e) {
                return "player not found";
            }
        }

        return ret.toString();
    }

    @PostMapping("/")
    String createUser(@RequestBody Player player) {
        if (player == null) {
            return "player is null.";
        }
        playerRepository.save(player);
        //find the user in the mysql and return some info back to AS
        JSONObject ret = new JSONObject();
        Player temp = playerRepository.findById(player.getId().intValue());
        ret.put("name", temp.getName());
        ret.put("email", temp.getEmail());
        return ret.toString();
    }


    @DeleteMapping("/delete/{id}")
    String removeUser(@PathVariable int id) {
        try {
            String nameDeleted = playerRepository.findById(id).getName();
            playerRepository.deleteById(id);
            return "Deleted: " + nameDeleted + " " + id;
        } catch (Exception e) {
            return "Failed";
        }
    }



}

