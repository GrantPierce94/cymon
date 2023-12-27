package SignIn.backend.Controller;

//import SignIn.backend.Model.CyMon;
import SignIn.backend.Model.Pokemon;
//import SignIn.backend.Repository.CyMonRepository;
import SignIn.backend.Repository.PokemonRepository;
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
public class PokemonController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PokemonRepository monRepository;

    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);


    @PostMapping("/mon/{email}")
    JSONObject addMon(@PathVariable String email, @RequestBody Pokemon mon) {
        Player p = playerRepository.findByEmail(email);
        p.addMonster(mon);
        mon.setPlayer(p);
        playerRepository.save(p);
        monRepository.save(mon);
        JSONObject ret = new JSONObject();
        ret.put("Species", mon.getSpecies());
        ret.put("Nickname", mon.getNickName());
        ret.put("PlayerEmail", p.getEmail());
        return ret;
    }

    @GetMapping("/mon/{email}")
    Pokemon getMon(@PathVariable String email, @RequestBody String name) {
        Player p = playerRepository.findByEmail(email);
        Pokemon ret = p.getMonster(name);
        playerRepository.save(p);
        return ret;
    }

    @DeleteMapping("/mon/{email}")
    void deleteMon(@PathVariable String email, @RequestBody String name) {
        Player p = playerRepository.findByEmail(email);
        p.deleteMonster(name);
        playerRepository.save(p);
    }
}
