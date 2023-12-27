package SignIn.backend.Model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Entity
@Data
public class Party {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String partyName;

    private int maxSize;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> members = new ArrayList<>();

    public Party() {}

    public Party(int n, String name) {
        this.maxSize = n;
        this.partyName = name;
    }

    public void addPlayer(Player player) {
        members.add(player);
    }

    public void setMaxSize(int n) { this.maxSize=n; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getMaxSize() { return maxSize; }

    public List<Player> getMembers() {
        return members;
    }
}
