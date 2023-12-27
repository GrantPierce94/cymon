package SignIn.backend.Model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Moves> moves;

    @Column(name = "didPlayerWin")
    private Boolean didPlayerWin = null;


    public List<Moves> getMoves() { return moves ; }

    public void addMoves(Moves moveToAdd) {
        if (moves == null) {
            moves = new ArrayList<>();
        }
        moves.add(moveToAdd);
    }

    public Game(Player player) {
        this.player = player;
    }

    public Game() {}

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getStatusOfGame() {
        if (didPlayerWin == null) {
            return -1;
        } else if (didPlayerWin) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setStatusOfGame(boolean didPlayerWin) {
        this.didPlayerWin = didPlayerWin;
    }

}
