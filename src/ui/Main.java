package ui;
import model.Controller;
import java.util.Scanner;

public class Main {
    private final Controller controller;
    private final Scanner sc;
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

        /*
        To play a new turn call the method rollDice
        To show the board, either showSnakesAndLadders or showBoard
        To finish the game, call the method hasGameFinished
         */
        controller.generateBoard(4, 5, 2, 3);
        controller.createPlayer("#");
        controller.createPlayer("$");
        controller.createPlayer("+");
        System.out.println(controller.showBoard());
        for(int i=0; i<10; i++) {
            System.out.println(controller.rollDice());
            System.out.println(controller.showSnakesAndLadders());
            System.out.println(controller.showBoard());
            if(controller.hasGameFinished()) {
                System.out.println("Game finished");
                break;
            }
        }

    }

    /** Shows the initial menu of the game
     * Show the main menu of the game on the console
     */
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

    /** Request the table values to create in the game
     * Requests the number of columns and rows to use to create the game table
     */
    private void play() {
        // Generating the board
        System.out.println("\nTo start the game, enter the number of rows and columns that the board will have (min. 4 x 4):");
        System.out.print("Rows: ");
        int rows = sc.nextInt();
        System.out.print("Columns: ");
        int columns = sc.nextInt();
        System.out.print("Snakes: ");
        int snakes = sc.nextInt();
        System.out.print("Ladders: ");
        int ladders = sc.nextInt();
        if (!controller.validateBoard(rows, columns, snakes, ladders)) {
            System.out.println("\nError. Invalid inputs.");
            play();
        } else {
            controller.generateBoard(rows, columns, snakes, ladders);
            sc.nextLine();
            System.out.println("\nSetting up players...");
            createPlayers(3, 0); //Define max players
            controller.startTimer();
            inGameMenu();
        }
    }

    /** Create the game players (3)
     * Request the symbols that each player in the game will use (3)
     * @param maxPlayers
     * @param counter
     */
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

    /** Print the menu of the game already initialized
     * Show available game actions on console
     */
    public void inGameMenu() {
        //Iniciar siempre mostrando el estado del tablero actual
        System.out.println(controller.showBoard());
        //Menu
        System.out.print("""
                \n-In Game-
                Player turn: xxx (configure)
                1. Roll dice
                2. Show snakes and ladders
                Option:\s""");
        int option = sc.nextInt();

        switch (option) {
            case 1 -> System.out.println("Put here the corresponding method.");
            case 2 -> System.out.println(controller.showSnakesAndLadders());
            default -> System.out.println("\nInvalid input");
        }
        inGameMenu();
    }
}