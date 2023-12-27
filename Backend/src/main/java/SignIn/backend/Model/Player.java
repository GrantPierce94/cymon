package SignIn.backend.Model;

import SignIn.backend.Repository.GameRepository;
import SignIn.backend.Repository.ItemRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    //Id generation for each player instance
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Defining list of friends associated with a player
    @ManyToMany
    private List<Player> friends = new ArrayList<>();

    // Defining list of games associated with a player
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Game> games;

    // Defining the list of items associated with a player
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pokemon> pokemons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "party_id")
    Party party;


    private String name; //name of player
    private String email; //email for player account
    private String password; //password for player account
    private int gamesPlayed; //games played by player
    private int wins;
    private int losses;

    private int balance;

    public Player(String name) {
        this.email = name;
    }

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.items = new ArrayList<>();
        this.balance = 100;
        this.wins = 0;
        this.losses = 0;
    }

    public void addLoss() { losses+=1; }

    public void addWin() { wins += 1; }

    public int getWins() { return wins; }

    public int getLosses() { return losses; }

    public void setParty(Party party) {
        this.party = party;
    }

    public Party getParty() { return party; }

    public Player() {
        this.balance = 100;
        this.items = new ArrayList<>();
    }

    public void addGamePlayed() {
        this.gamesPlayed += 1;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int n) {
        this.gamesPlayed += n;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        if (this.games == null) {
            games = new ArrayList<>();
        }
        games.add(game);
        game.setPlayer(this);
    }

    // Getter for items list
    public List<Item> getItems() {
        return items;
    }

    // Setter for items list
    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Method to add an item to a player's list of items
    public void addItem(Item item) {
        items.add(item);
        item.setPlayer(this);
    }

    // Get specific item
    public Item getItem(Item item) {
        String name = item.getName();

        for(int i = 0; i <= items.size(); i++){
            if(name.equals(items.get(i).getName())){
                return items.get(i);
            }
        }
        return null;
    }

    // Get specific item
    public void removeItem(Item item) {
        String name = item.getName();

        for(int i = 0; i <= items.size(); i++){
            if(name.equals(items.get(i).getName())){
                items.remove(i);
            }
        }

    }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public void addFriend(Player player) { friends.add(player); }

    public List<Player> getFriends() { return friends; }

    public void addMonster(Pokemon mon){
        this.pokemons.add(mon);
    }

    public Pokemon getMonster(String name){
        Pokemon ret = null;
        for(Pokemon f: pokemons){
            if(f.getNickName().equals(name)){
                ret = f;
            }
        }
        return ret;
    }

    public void deleteMonster(String name){

        for(Pokemon f: pokemons){
            if(f.getNickName().equals(name)){
                this.pokemons.remove(f);
            }
        }

    }

}
