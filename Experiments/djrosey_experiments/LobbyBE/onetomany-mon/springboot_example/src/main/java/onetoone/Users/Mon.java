package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Mon {

    private String mon;

    private String type;

    private double hp;

    private double attack;

    private boolean claimed;

    public Mon(){

    }

    public Mon(String mon, String type, double hp, double attack) {
        this.mon = mon;
        this.type = type;
        this.hp = hp;
        this.attack = attack;
        this.claimed = false;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setClaimed(){
        this.claimed = true;
    }

    public boolean isClaimed(){
        return claimed;
    }

    @Override
    public String toString() {
        return mon + " "
                + type + " "
                + hp + " "
                + attack;
    }
}
