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
        System.out.print("""
                \n///// Hello, welcome to Snakes and Ladders /////
                1. Play
                2. Exit
                Option:\s""");

        int option = sc.nextInt();

        switch(option) {
            case 1 -> play();
            case 2 -> System.out.println("Exit done.");
            default -> {
                System.out.println("Invalid option.");
                showMenu();
            }
        }
    }

    private void play() {
        // Generating the board

        System.out.println("\nTo start the game, enter the number of rows and columns that the board will have (min. 4 x 4):");
        System.out.print("Rows: ");
        int rows = sc.nextInt();
        System.out.print("Columns: ");
        int columns = sc.nextInt();
        if (controller.validateBoard(rows, columns) == false) {
            System.out.println("Error. Invalid inputs.");
            play();
        } else {
            System.out.println("Setting up game...");
            createPlayers(3, 1); //Define max players
        }
    }

    public void createPlayers(int maxPlayers, int counter) {
        //When counter == maxPlayers -> stop
        //Select symbols for each player
        if (counter <= maxPlayers) {
            System.out.println("Choose a symbol for player " + counter + "\n* ! O X % $ # + &");
            char sb = sc.next().charAt(0);

        } else {
            System.out.println("Setting up game...");
        }

    }
}