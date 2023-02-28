package ui;
import model.Controller;

public class Main {
    private Controller controller;
    public Main() {
        this.controller = new Controller();
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.showMenu();
    }

    private void showMenu() {

        System.out.println("hello");
    }
}