package SignIn.backend.Model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    //ID generation for each instance of item
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String effect;
    private int magnitude;

    private int cost;



    // Establishing the relationship with Player
    @ManyToOne
    private Player player;




    public Item(String name, String effect, int magnitude, int cost) {
        this.name = name;
        this.effect = effect;
        this.magnitude = magnitude;
        this.cost = cost;
    }

    public Item() {

    }

    // GETTERS/////////////////////////////////
    public Player getPlayer() {
        return player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public int getCost() {
        return cost;
    }

    //SETTERS//////////////////////////////
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
