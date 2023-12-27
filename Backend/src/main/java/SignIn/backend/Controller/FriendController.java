package SignIn.backend.Controller;


import SignIn.backend.Model.Player;
import SignIn.backend.Repository.PlayerRepository;
import jakarta.transaction.Transactional;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@ServerEndpoint("/player/{playerName}")
@Component
public class FriendController {

    private static PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository repo) { playerRepository = repo;}

    private static Map<Session, Player> sessionPlayerMap = new Hashtable<>();
    private static Map<Player, Session> playerSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("playerName") String playerName) {
        if (!playerName.isEmpty()) {
            logger.info("[Player onOpen] " + playerName);
            try {
                Player temp = playerRepository.findByEmail(playerName);
                sessionPlayerMap.put(session, temp);
                playerSessionMap.put(temp, session);
                session.getBasicRemote().sendText("connected: " + playerName);
            } catch (Exception e) {
                logger.info("[Player onOpen Error] " + e.getMessage());
            }
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("[onMessage] " + message);
        try {
            Player player = sessionPlayerMap.get(session);
            if (message.contains("add")) {
                String[] m = message.split(" ");
                String name = m[1];
                searchNames(session, player, name);
            } else if (message.equals("getFriends")) {
                sendFriends(session, player, message);
            }
        } catch (Exception e) {
            logger.info("[Player searching error] " + e.getMessage());
        }
    }

    @OnClose
    public void close(Session session) {
        logger.info("Closed Friend session: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Error or disconnection from Friend connection: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    public void sendFriends(Session session, Player player, String name) {
        try {
            JSONArray obj = new JSONArray();
            for (Player p : player.getFriends()) {
                obj.put(p.getName());
            }
            logger.info("[Sent] " + obj.toString());
            session.getBasicRemote().sendText(obj.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void searchNames(Session session, Player player, String name) {
        try {
            Player searchedPlayer = playerRepository.findByName(name);
            player.addFriend(searchedPlayer);
            playerRepository.save(player);
            session.getBasicRemote().sendText(searchedPlayer.getName());
        } catch (Exception e) {
            logger.info("[Error inside searchNames] " + e.getMessage());
        }
    }



}
