package SignIn.backend.Controller;

import SignIn.backend.Model.Game;
import SignIn.backend.Model.Moves;
import SignIn.backend.Model.Player;
import SignIn.backend.Repository.GameRepository;
import SignIn.backend.Repository.MovesRepository;
import SignIn.backend.Repository.PlayerRepository;
import SignIn.backend.Service.GameService;
import jakarta.transaction.Transactional;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Map;

@RestController
@ServerEndpoint("/gamesocket/{playerEmail}")
@Component
public class GameSocketController {

    private static PlayerRepository playerRepository;
    private static GameRepository gameRepository;
    private static MovesRepository movesRepository;

    private static GameService gameService;

    @Autowired
    public void setPlayerRepository(PlayerRepository repo) { playerRepository = repo; }

    @Autowired
    public void setGameRepository(GameRepository repo) { gameRepository = repo; }

    @Autowired
    public void setMovesRepository(MovesRepository repo) { movesRepository = repo; }

    @Autowired
    public GameSocketController(GameService gService) {
        gameService = gService;
    }

    private final Logger logger = LoggerFactory.getLogger(GameSocketController.class);

    public GameSocketController() {}

    //WebSocket event methods:

    @OnOpen
    public void onOpen(Session session, @PathParam("playerEmail") String playerEmail) {
        logger.info("[GameSocket onOpen] " + playerEmail);

        Player p = playerRepository.findByEmail(playerEmail);
        gameService.getSessionPlayerMap().put(session, p);
        gameService.getPlayerSessionMap().put(p, session);
        gameService.getEmailPlayerMap().put(playerEmail, p);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        Player p = gameService.getSessionPlayerMap().get(session);
        logger.info("[onMessage] " + message + " " + p.getName());

        //"[openGame]" - add a Game that is currently happening to save Moves to it
        if (message.equals("[openGame]")) {
            gameService.openActiveGame(p);

        } else if (message.startsWith("[closeGame]")) {
            String[] s = message.split(":");
            String boolString = s[1].trim();
            boolean gameResult = Boolean.parseBoolean(boolString);

            gameService.closeActiveGame(p, gameResult);
        }
    }

    @OnClose
    public void close(Session session) {
        logger.info("[GameSocket close]");
        try {
            session.close();
        } catch (IOException e) {
            logger.info("[GS close] " + "error closing session");
            logger.error(e.getMessage());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("[GS onError]");
        throwable.printStackTrace();
        logger.info("[msg]" + throwable.getMessage());
    }

    @PostMapping("/gamesocket/{playerEmail}")
    public String postMove(@PathVariable String playerEmail, @RequestBody Moves move) {
        logger.info("[controller postMove called]");
        JSONObject response = gameService.handleMoveMade(playerEmail, move);
        return response.toString();
    }




}

//controller looks good and works on the server great work! -grant