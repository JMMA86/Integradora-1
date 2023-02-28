package model;

public class Slot extends Node {
    private int value;

    public Slot(int value) {
        super();
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
