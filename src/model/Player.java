package model;

public class Player extends Node {
    private char id;
    private Slot currentSlot;

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

    public Slot getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(Slot currentSlot) {
        this.currentSlot = currentSlot;
    }
}
