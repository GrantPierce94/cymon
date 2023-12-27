package SignIn.backend.Model;

import jakarta.persistence.*;




import jakarta.persistence.*;
import SignIn.backend.Repository.GameRepository;
import SignIn.backend.Repository.ItemRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import java.util.List;


@Entity
@Table(name = "move")
public class Moves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "level")
    private int level;

    @Column(name = "category")
    private String category;

    @Column(name = "damage_class")
    private String damageClass;

    @Column(name = "target")
    private String target;

    @Column(name = "type")
    private String type;

    @Column(name = "accuracy")
    private Integer accuracy; // nullable

    @Column(name = "heal")
    private int heal;

    @Column(name = "power")
    private Integer power; // nullable

    @Column(name = "pp")
    private int pp;

    @Column(name = "ailment")
    private String ailment;

    @Column(name = "ailment_chance")
    private int ailmentChance;

    @Transient // This value is derived and doesn't need to be stored in the database
    private boolean isUsable;

    @Transient // maxPP is the same as pp, so it doesn't need a separate column
    private final int maxPP = pp;

    @Transient // currentPP is a state that can change frequently and might not need persistence
    private int currentPP;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // Constructors, getters, setters, and other methods
    // ...

    @PostLoad // Method to set the transient fields after loading the entity from the database
    private void onLoad() {
        this.isUsable = this.pp > 0;
        this.currentPP = this.maxPP;
    }

    // Other business logic methods can be added as needed
    public void setPlayer(Player player) { this.player = player; }

    public Player getPlayer() { return player; }

    public String getName() { return name; }

    public void setGame(Game g) { this.game = g; }

    public Game getGame() { return game; }
}