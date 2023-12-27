package SignIn.backend.Model;

import SignIn.backend.Repository.PlayerRepository;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Entity
public class CyMon {

    //ID generation for each instance of item
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //mon name
    private String name;

    //mon hp
    private double hp;

    //current hp of mon
    private double currentHp;

    //death status of mon
    private boolean dead;

    //mon is currently being used by player in battle
    private boolean battling;

    // Establishing the relationship with Player
    @ManyToOne
    private Player player;




    public CyMon(String name, double hp, boolean dead) {
        this.name = name;
        this.hp = hp;
        this.currentHp = hp; //sets default current hp to max hp
        this.dead = false;
        this.battling = true;
        this.player = null;
    }

    public CyMon(int id, double damage) {

    }

    public CyMon() {

    }


    //GETTERS////////////

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHp() {
        return hp;
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public boolean isDead() {
        return dead;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isBattling() {return battling;}


    //SETTERS////////////////////////////////


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setCurrentHp(double currenthp) {
        this.currentHp = currenthp;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBattling(boolean status){ battling = status; }

    ////////////////////////////////////////////////


}