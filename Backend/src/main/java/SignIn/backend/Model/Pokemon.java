package SignIn.backend.Model;

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
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "species")
    private String species;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "level")
    private int level;

    // Omitting image URLs and Bitmaps as they are not typically stored directly in databases.
    // You would usually store URLs or references to where the images are stored.

    @Column(name = "base_experience_reward")
    private int baseExperienceReward;

    @Column(name = "base_stat_attack")
    private int baseStatAttack;

    @Column(name = "base_stat_defense")
    private int baseStatDefense;

    @Column(name = "base_stat_hp")
    private int baseStatHp;

    @Column(name = "base_stat_special_attack")
    private int baseStatSpecialAttack;

    @Column(name = "base_stat_special_defense")
    private int baseStatSpecialDefense;

    @Column(name = "base_stat_speed")
    private int baseStatSpeed;

    @Column(name = "current_experience")
    private float currentExperience;

    @Column(name = "is_dead")
    private boolean isDead;

    public Pokemon(Long id, Player player, String species, String nickName, int level, int baseExperienceReward, int baseStatAttack, int baseStatDefense, int baseStatHp, int baseStatSpecialAttack, int baseStatSpecialDefense, int baseStatSpeed, float currentExperience, boolean isDead, List<String> types) {
        this.id = id;
        this.player = player;
        this.species = species;
        this.nickName = nickName;
        this.level = level;
        this.baseExperienceReward = baseExperienceReward;
        this.baseStatAttack = baseStatAttack;
        this.baseStatDefense = baseStatDefense;
        this.baseStatHp = baseStatHp;
        this.baseStatSpecialAttack = baseStatSpecialAttack;
        this.baseStatSpecialDefense = baseStatSpecialDefense;
        this.baseStatSpeed = baseStatSpeed;
        this.currentExperience = currentExperience;
        this.isDead = isDead;
        this.types = types;
    }

    public Pokemon(){

    }

    // Relationships like moves and types can be more complex. Here's an example with types.
    @ElementCollection
    @CollectionTable(name = "pokemon_types", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "type")
    private List<String> types;

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public String getNickName() {
        return nickName;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseExperienceReward() {
        return baseExperienceReward;
    }

    public int getBaseStatAttack() {
        return baseStatAttack;
    }

    public int getBaseStatDefense() {
        return baseStatDefense;
    }

    public int getBaseStatHp() {
        return baseStatHp;
    }

    public int getBaseStatSpecialAttack() {
        return baseStatSpecialAttack;
    }

    public int getBaseStatSpecialDefense() {
        return baseStatSpecialDefense;
    }

    public int getBaseStatSpeed() {
        return baseStatSpeed;
    }

    public float getCurrentExperience() {
        return currentExperience;
    }

    public boolean isDead() {
        return isDead;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBaseExperienceReward(int baseExperienceReward) {
        this.baseExperienceReward = baseExperienceReward;
    }

    public void setBaseStatAttack(int baseStatAttack) {
        this.baseStatAttack = baseStatAttack;
    }

    public void setBaseStatDefense(int baseStatDefense) {
        this.baseStatDefense = baseStatDefense;
    }

    public void setBaseStatHp(int baseStatHp) {
        this.baseStatHp = baseStatHp;
    }

    public void setBaseStatSpecialAttack(int baseStatSpecialAttack) {
        this.baseStatSpecialAttack = baseStatSpecialAttack;
    }

    public void setBaseStatSpecialDefense(int baseStatSpecialDefense) {
        this.baseStatSpecialDefense = baseStatSpecialDefense;
    }

    public void setBaseStatSpeed(int baseStatSpeed) {
        this.baseStatSpeed = baseStatSpeed;
    }

    public void setCurrentExperience(float currentExperience) {
        this.currentExperience = currentExperience;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    // Add any business logic methods (like levelUp, calculateStats, etc.)
    // ...
}