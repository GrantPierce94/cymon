package SignIn.backend.Service;

import SignIn.backend.Model.Game;
import SignIn.backend.Model.Moves;
import SignIn.backend.Model.Player;
import SignIn.backend.Repository.GameRepository;
import SignIn.backend.Repository.MovesRepository;
import SignIn.backend.Repository.PlayerRepository;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MovesRepository movesRepository;

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Map that contains all session/player connections during this instance of the server running
     */
    private static final Map<Session, Player> sessionPlayerMap = new Hashtable<>();
    /**
     * Map that allows us to send a message to a certain client active on the server
     */
    private static final Map<Player, Session> playerSessionMap = new Hashtable<>();
    /**
     * Map that allows management and addition of moves to an active game
     */
    private static final Map<Player, Game> playerGameMap = new Hashtable<>();
    /**
     * Map that allows for just one instance of Player on the backend
     */
    private static final Map<String, Player> emailPlayerMap = new Hashtable<>();

    Logger logger = LoggerFactory.getLogger( GameService.class);

    @Transactional
    public void openActiveGame(Player player) {

        Game g = gameRepository.save(new Game(player));
        logger.info("[openActiveGame call]");

        player.addGame(g);

        playerGameMap.put(player, g);
    }

    @Transactional
    public void closeActiveGame(Player p, boolean didPlayerWin) {
        logger.info("[closeActiveGame call]");
        Game temp = playerGameMap.get(p);

        temp.setStatusOfGame(didPlayerWin);
        if (didPlayerWin) {
            p.addWin();
        } else {
            p.addLoss();
        }

        // Save the finished game to the player, remove from active games map
        p.addGame(temp);
        p.addGamePlayed();
//        playerGameMap.remove(p, temp);
        gameRepository.save(temp);
        playerRepository.save(p);
    }


    /**
     * Save the move made by player 'p'
     * @param playerEmail - player which made the move
     * @param m - move made by player
     */
    @Transactional
    public JSONObject handleMoveMade(String playerEmail, Moves m) {
        logger.info("handleMoveMade");
        Player temp = emailPlayerMap.get(playerEmail);
        Game g = playerGameMap.get(temp);

        m.setPlayer(temp);
        m.setGame(g);

        movesRepository.save(m);
        g.addMoves(m);

        JSONObject ret = new JSONObject();
        ret.put("playerName", temp.getName());
        ret.put("moveCount", g.getMoves().size());
        ret.put("moveName", m.getName());
        return ret;
    }

    public Map<Player, Session> getPlayerSessionMap() {
        return playerSessionMap;
    }

    public Map<Session, Player> getSessionPlayerMap() {
        return sessionPlayerMap;
    }

    public Map<String, Player> getEmailPlayerMap() { return emailPlayerMap; }

}
