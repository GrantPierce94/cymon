package coms309.people;

public class Player {
    private String name;
    private String monIn;
    private Mon monster;

    public Player(String name, String monIn) {
        this.name = name;
        this.monIn = monIn;
        this.monster = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mon getMonster() {
        return monster;
    }

    public void setMonster(Mon monster) {
        this.monster = monster;
    }

    public String getMonIn() {
        return monIn;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", monster=" + monster +
                '}';
    }

}
