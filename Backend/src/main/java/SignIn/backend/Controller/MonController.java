//package SignIn.backend.Controller;
//
//import SignIn.backend.Model.CyMon;
//import SignIn.backend.Repository.CyMonRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import SignIn.backend.Model.Player;
//import SignIn.backend.Model.Game;
//import SignIn.backend.Repository.PlayerRepository;
//import SignIn.backend.Repository.GameRepository;
//import org.json.JSONObject;
//
//
//import java.util.Iterator;
//import java.util.List;
//
//@RestController
//public class MonController {
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    @Autowired
//    GameRepository gameRepository;
//
//    @Autowired
//    CyMonRepository monRepository;
//
//    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);
//
//    /**
//     * @param defenderPlayerId id of the player that is getting attacked
//     * @param damage amount of damage to be done to the player's battling mon
//     * @return what damage was done to the cyMon and if the mon fainted
//     */
//    @PostMapping("/attack/{defenderPlayer}")
//    String monAttack(@PathVariable String defenderPlayer, @RequestParam int damage) {
//
//    //sets attacker and defender
//    //Player attacker = playerRepository.getReferenceById(attackerPlayerId);
//    Player defender = playerRepository.findByEmail(defenderPlayer);
//    CyMon mon = defender.getBattlingMon();
//
//        if (defender == null) {
//            return "Invalid player IDs";
//        }
//
//    if(mon != null && !mon.isDead()){ //if there is a defender mon and if it is not dead attack
//        double currentHp = mon.getCurrentHp();
//        double hpAfterAttack = mon.getCurrentHp() - damage;
//
//        //update hp if mon is not fainted
//        if(hpAfterAttack > 0){
//            mon.setCurrentHp(hpAfterAttack);
//            playerRepository.save(defender);
//            monRepository.save(mon);
//            return "Battling mon has taken" + damage + "damage";
//        }
//        //updates hp to zero and faints mon
//        else{
//            mon.setCurrentHp(0);
//            mon.setDead(true);
//            playerRepository.save(defender);
//            monRepository.save(mon);
//            return "Battling mon has fainted";
//        }
//
//    }
//    //nothing has happened
//    return "fuck";
//    }
//
//
//    /**
//     * @param owner owner of monster to be added
//     * @param mon monster added
//     */
//    @PostMapping("/addMon/{email}")
//    void addMon(@PathVariable String email, @RequestBody CyMon mon) {
//        Player p = playerRepository.findByEmail(email);
//        p.addMonster(mon);
//        playerRepository.save(p);
//    }
//
//
//}
