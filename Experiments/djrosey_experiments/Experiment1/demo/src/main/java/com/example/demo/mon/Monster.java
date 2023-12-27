package com.example.demo.mon;

import java.time.LocalDate;

public class Monster {


    private String name;
    private double hp;
    private double attack;
    private double defense;
    private double specialDefense;
    private double specialAttack;
    private double speed;

    public Monster(String name, double hp, double attack, double defense, double specialDefense, double specialAttack, double speed) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.specialAttack = specialAttack;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public double getHp() {
        return hp;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getSpecialDefense() {
        return specialDefense;
    }

    public double getSpecialAttack() {
        return specialAttack;
    }

    public double getSpeed() {
        return speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setSpecialDefense(double specialDefense) {
        this.specialDefense = specialDefense;
    }

    public void setSpecialAttack(double specialAttack) {
        this.specialAttack = specialAttack;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Mon{" +
                "name=" + name +
                ", hp='" + hp + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                ", specialDefense='" + specialDefense + '\'' +
                ", specialAttack='" + specialAttack + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}
