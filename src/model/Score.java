package model;

public class Score extends Node {
    private String name;
    private double value;
    private Score left;
    private Score right;

    public Score(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Score getRight() {
        return right;
    }

    public void setRight(Score right) {
        this.right = right;
    }

    public Score getLeft() {
        return left;
    }

    public void setLeft(Score left) {
        this.left = left;
    }
}
