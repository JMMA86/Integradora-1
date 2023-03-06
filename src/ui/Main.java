package ui;
import model.Controller;
import java.util.Scanner;

public class Main {
    private Controller controller;
    private Scanner sc;
    public Main() {
        this.controller = new Controller();
        this.sc = new Scanner(System.in);
    }
    public static void main(String[] args) {
        Main main = new Main();
        // main.test();
        main.showMenu();
    }
    private void test() {
        // En este lugar puedes liarla parda
    }

    private void showMenu() {
        System.out.println("\n///// Hello, welcome to Snakes and Ladders /////\n");

        // Generating the board

        System.out.println("To start the game, enter the number of rows and columns that the board will have.");
        System.out.print("rows: ");
        int rows = sc.nextInt();
        System.out.print("columns: ");
        int columns = sc.nextInt();
        controller.generateBoard(rows, columns);

        System.out.println(controller.showBoard());
    }
}