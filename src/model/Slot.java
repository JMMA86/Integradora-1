package model;

public class Slot extends Node {
    private int value;
    private int steps;

    public Slot(int value) {
        super();
        this.value = value;
        this.steps = 1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
