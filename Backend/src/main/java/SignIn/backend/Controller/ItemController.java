package SignIn.backend.Controller;

import SignIn.backend.Model.Item;
import SignIn.backend.Repository.ItemRepository;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@ServerEndpoint("/item/{playerName}")
@Component
public class ItemController {

    private static PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository repo) { playerRepository = repo;}

    private static ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository repo) { itemRepository = repo;}


    private static Map<Session, Player> sessionPlayerMap = new Hashtable<>();
    private static Map<Player, Session> playerSessionMap = new Hashtable<>();


    private final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("playerName") String playerName) {
        if (!playerName.isEmpty()) {
            logger.info("[Item onOpen] " + playerName);
            try {
                Player temp = playerRepository.findByEmail(playerName);
                playerSessionMap.put(temp, session);
                sessionPlayerMap.put(session, temp);
                session.getBasicRemote().sendText("connected: " + playerName);
            } catch (Exception e) {
                logger.info("[Item onOpen Error] " + e.getMessage());
            }
        }
    }


    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("[onMessage] " + message);
        try {
            Player player = sessionPlayerMap.get(session);
            if (message.contains("buy")) {

                String[] m = message.split(" ");
                String name = m[1]; //
                int price = Integer.parseInt(name);

                boolean canBuy = false;

                if(player.getBalance() - price >= 0){
                    canBuy =  true;
                }

                if(canBuy){
                    player.setBalance(player.getBalance() - price);
                    playerRepository.save(player);
                }
                else{
                    logger.info("Player cannot buy item");
                }
                logger.info("" + player.getBalance());
                session.getBasicRemote().sendText("balance " + player.getBalance());
            }
            else if(message.equals("getBalance")){
                logger.info("" + player.getBalance());
                session.getBasicRemote().sendText("balance " + player.getBalance());
            }
        } catch (Exception e) {
            logger.info("[Item Balance Update Error] " + e.getMessage());
        }
    }

    @OnClose
    public void close(Session session) {
        logger.info("Closed Item session: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Error or disconnection from Item connection: " + session.getId());
        try {
            sessionPlayerMap.remove(session);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }





    /**
     * @param id player id
     * @param item item to add
     */
    //adds item to specified player
    @PostMapping("/item/{email}")
    String addItem(@PathVariable String email, @RequestBody Item item) {
        try {
            JSONObject itemSent = new JSONObject(item);
            logger.info(itemSent.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        Player player = playerRepository.findByEmail(email);
        player.addItem(item);
        itemRepository.save(item);
        playerRepository.save(player);
        JSONObject obj = new JSONObject();
        obj.put("name", item.getName());
        return obj.toString();
    }

    /**
     * @param id Player id
     * @param item Item to fetch
     * @return returns item of the name that you specified
     */
    @GetMapping("/item/{email}")
    Item getItem(@PathVariable String email, @RequestBody Item item) {
        Player player = playerRepository.findByEmail(email);
        return player.getItem(item);
    }

    @DeleteMapping("/item/delete/{email}")
    void deleteItem(@PathVariable String email, @RequestBody Item item) {
        Player player = playerRepository.findByEmail(email);
        player.removeItem(item);
    }



}