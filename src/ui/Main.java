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
        // Testing
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
            System.out.println("\nError. Invalid inputs.");
            play();
        } else {
            sc.nextLine();
            System.out.println("\nSetting up players...");
            createPlayers(3, 0); //Define max players
            inGameMenu();
        }
    }

    public void createPlayers(int maxPlayers, int counter) {
        //When counter == maxPlayers -> stop
        //Select symbols for each player
        if (counter < maxPlayers) {
            System.out.println("Choose a symbol for player " + (counter + 1) + "\n* ! O X % $ # + &");
            if (controller.createPlayer(sc.nextLine()).equals("Created player.")) {
                createPlayers(maxPlayers, ++counter);
            } else {
                System.out.println("\nError, invalid symbol or chosen by another player. Try again.\n");
                createPlayers(maxPlayers, counter);
            }
        } else {
            System.out.println("\nSetting up game...");
        }
    }

    public void inGameMenu() {
        System.out.print("""
                \n-In Game-
                Player turn: xxx (configure)
                1. Roll dice
                2. Show board
                3. Show snakes and ladders
                Option: """);
        int option = sc.nextInt();
        //Create switch
    }
}