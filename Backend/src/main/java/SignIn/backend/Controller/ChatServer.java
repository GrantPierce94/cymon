package SignIn.backend.Controller;

import SignIn.backend.Model.Party;
import SignIn.backend.Model.Player;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/chat/{chatName}/{username}")
@Component
public class ChatServer {
    private static Map<Session, String> sessionUsernameMap = new Hashtable();
    private static Map<String, Session> usernameSessionMap = new Hashtable();
    private static Map<Session, Player> sessionPlayerMap = new Hashtable<>();
    private static Map<Player, Session> playerSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static Party roomA = new Party(10, "RoomA");
    private static Party roomB = new Party(10, "RoomB");
    private static Party roomC = new Party(10, "RoomC");
    private static Party roomD = new Party(10, "RoomC");
//    private static Party[] rooms = {roomA, roomB, roomC, roomD};

    public ChatServer() {

    }

    @OnOpen
    public void onOpen(Session session, @PathParam("chatName") String chatName, @PathParam("username") String username) throws IOException {
        this.logger.info("[onOpen] " + username);
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        } else {
            Player p = new Player();
            p.setName(username);
            p.setParty(roomA);
            roomA.addPlayer(p);
            roomB.addPlayer(p);
            roomC.addPlayer(p);
            roomD.addPlayer(p);

            sessionPlayerMap.put(session, p);
            playerSessionMap.put(p, session);
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);
            this.sendMessageToPArticularUser(username, "Welcome to the chat server, " + username);
            this.broadcast("User: " + username + " has Joined the Chat", p.getParty());
        }

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String username = (String) sessionUsernameMap.get(session);
        Player temp = sessionPlayerMap.get(session);
        this.logger.info("[onMessage] " + username + ": " + message);
        if (message.startsWith("@")) {
            String[] split_msg = message.split("\\s+");
            StringBuilder actualMessageBuilder = new StringBuilder();

            for (int i = 1; i < split_msg.length; ++i) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }

            String destUserName = split_msg[0].substring(1);
            String actualMessage = actualMessageBuilder.toString();
            this.sendMessageToPArticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
            this.sendMessageToPArticularUser(username, "[DM from " + username + "]: " + actualMessage);
        } else if (message.startsWith("[switch]")) {
            String[] msg = message.split(":");
            String roomName = msg[1];
            joinRoom(session, roomName);
        } else {
            logger.info("[temp party]" + temp.getParty().getPartyName());
            this.broadcast(username + ": " + message, temp.getParty());
        }

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = (String) sessionUsernameMap.get(session);
        Player temp = sessionPlayerMap.get(session);
        this.logger.info("[onClose] " + username);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        this.broadcast(username + " disconnected", temp.getParty());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = (String) sessionUsernameMap.get(session);
        this.logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    private void sendMessageToPArticularUser(String username, String message) {
        try {
            ((Session) usernameSessionMap.get(username)).getBasicRemote().sendText(message);
        } catch (IOException var4) {
            this.logger.info("[DM Exception] " + var4.getMessage());
        }

    }

    private void joinRoom(Session session, String roomName) {
        Player temp = sessionPlayerMap.get(session);

        if (roomName.equals("BadgeBandits")) {
            temp.setParty(roomA);
        } else if (roomName.equals("GymGladiators")) {
            temp.setParty(roomB);
        } else if (roomName.equals("RocketRebels")) {
            temp.setParty(roomC);
        } else if (roomName.equals("CymonScholars")) {
            temp.setParty(roomD);
        }

        logger.info("[switch]" + temp.getParty().getPartyName());
        broadcast(temp.getName() + " has joined the room.", temp.getParty());
    }


    private void broadcast(String message, Party p) {

        sessionPlayerMap.forEach((session, player) -> {
            if (player.getParty() == p) {
                try {
                    logger.info("[send]" + message);
                    session.getBasicRemote().sendText(message);
                } catch (IOException var5) {
                    this.logger.info("[Broadcast Exception] " + var5.getMessage());
                }
            }
        });

    }
}