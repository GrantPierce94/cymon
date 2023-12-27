package SignIn.backend.Controller;

import SignIn.backend.Model.Party;
import SignIn.backend.Model.Player;
import SignIn.backend.Repository.PartyRepository;
import SignIn.backend.Repository.PlayerRepository;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Synchronized;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@ServerEndpoint("/party/{playerName}")
@Component
public class PartyController {

    private static PartyRepository partyRepository;

    @Autowired
    public void setPartyRepository(PartyRepository repo) {
        partyRepository = repo;
    }


    private static PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository repo) { playerRepository = repo;}

    private static Map<String, Session> nameSessionMap = new Hashtable<>();
    private static Map<Session, Party> sessionPartyMap = new Hashtable<>();
    private static Map<Session, Player> sessionPlayerMap = new Hashtable<>();
    private static Map<Party, Session> partySessionMap = new Hashtable<>();
    private static Map<Session, Boolean> isReadyUpMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(PartyController.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("playerName") String playerName) {
        if (!playerName.isEmpty()) {
            logger.info("[Party onOpen] " + playerName);
            try {
                Player temp = playerRepository.findByEmail(playerName);
                sessionPlayerMap.put(session, temp);
                nameSessionMap.put(playerName, session);
                session.getBasicRemote().sendText("connected " + playerName);
            } catch (Exception e) {
                logger.info("[Party onOpen error] " + e.getMessage());
            }
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("[onMessage] " + message);
        if (message.equals("readyUp")) {
            isReadyUpMap.put(session, true);
            Party temp = sessionPartyMap.get(session);
            Boolean isReady = handlePartyReady(temp);
            try {
                if (isReady) {
                    broadcast("true", temp);
                } else {
                    broadcast("false", temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (message.equals("getParties")) {
            sendParties(session);
        } else if (message.contains("joinParty")) {
            try {
                String[] m = message.split(" ");
                String playerEmail = m[1];
                Player player = playerRepository.findByEmail(playerEmail);
                sessionPlayerMap.put(session, player);
                isReadyUpMap.put(session, false);
                String partyName = m[2];
                Party party = partyRepository.findByPartyName(partyName);
                joinParty(session, player, party);
            } catch (Exception e) {
                logger.info("[Error on message] " + e.getMessage());
            }
        } else if (message.contains("createParty")) {
            try {
                String[] m = message.split(" ");
                String partyName = m[1];
                String playerEmail = m[3];
                Player tempPlayer = playerRepository.findByEmail(playerEmail);
                int partySize = Integer.parseInt(m[2]);
                if (nameSessionMap.containsKey(partyName)) {
                    session.getBasicRemote().sendText("Party already exists");
                } else {
                    Party party = new Party();
//                    Party party = partyRepository.save(new Party());
                    party.setMaxSize(partySize);
                    party.setPartyName(partyName);
                    try {
                        sessionPartyMap.put(session, party);
                        partyRepository.save(party);
                        joinParty(session, tempPlayer, party);
                        playerRepository.save(tempPlayer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    playerRepository.save(tempPlayer);
//                    partyRepository.save(party);

                }
            } catch (Exception e) {
                logger.info("[Error creating party] " + e.getMessage());
            }
        } else if (message.equals("getPlayers")) {
            try {
                Party party = sessionPartyMap.get(session);
                if (party!=null) {
                    getPlayers(session, party);
                }
            } catch (Exception e) {
                logger.info("[Party not found in map] " + e.getMessage());
            }
        }
    }

    @OnClose
    public void close(Session session) {
        logger.info("Closed Party session: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Error or disconnection from Party connection: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }



    public void joinParty(Session session, Player player, Party party) {
        try {
            partySessionMap.put(party, session);
            sessionPartyMap.put(session, party);
            party.addPlayer(player);
            player.setParty(party);
        } catch (Exception e) {
            logger.info("[Error joinParty] " + e.getMessage());
        }
    }

    public void getPlayers(Session session, Party party) {
        try {
            JSONArray arr = new JSONArray();
            for (Player p : party.getMembers()) {
                arr.put(p.getName());
            }
            logger.info("[sent getPlayers] " + arr.toString());
            session.getBasicRemote().sendText(arr.toString());
        } catch (Exception e) {
            logger.info("[error in getPlayers] " + e.getMessage());
        }
    }

    public boolean handlePartyReady(Party party) {
        return isReadyUpMap.containsValue(false);
    }

    public void sendParties(Session session) {
        try {
                sessionPartyMap.forEach((s, p) -> {
                    JSONObject container = new JSONObject();
                    container.put("Name", p.getPartyName());
                    int max = p.getMaxSize();
                    int members = p.getMembers().size();
                    container.put("Available", max - members);
                    container.put("Max", max);
                    try {
                        session.getBasicRemote().sendText(container.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }
    }


    public void broadcast(String message, Party p) throws IOException {
        String partyName = p.getPartyName();
        partySessionMap.forEach((party, session) -> {
           String tempPartyName = party.getPartyName();
           if (tempPartyName.equals(partyName)) {
               try {
                   session.getBasicRemote().sendText(message);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        });
    }


}
