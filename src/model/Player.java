package model;

public class Player extends Node {
    private char id;

    public Player(char id) {
        super();
        this.id = id;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }
}
