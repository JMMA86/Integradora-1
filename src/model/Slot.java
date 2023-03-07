package model;

public class Slot extends Node {
    private int value;
    private int steps;
    private String linkId;

    public Slot(int value) {
        super();
        this.value = value;
        this.steps = 0;
        this.linkId = null;
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

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
