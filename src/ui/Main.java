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

    /**
     * Just used for testing
     */
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
        System.out.println("\nTo start the game, enter the number of rows and columns that the board will have (min. 4 x 4)." +
                "\nAlso, snakes and ladders can´t exceed 40% of the generated board.");
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
        //Show board
        System.out.println(controller.showBoard());
        //Menu
        System.out.print("""
                \n-In Game-
                Player turn:\s""" + controller.getCurrentTurn() + "\s[" + controller.getCurrentSymbol() + """
                ]\n1. Roll dice
                2. Show snakes and ladders
                Option:\s""");
        int option = sc.nextInt();

        switch (option) {
            case 1 -> System.out.println(controller.rollDice());
            case 2 -> System.out.println(controller.showSnakesAndLadders());
            default -> System.out.println("\nInvalid input");
        }

        if(controller.hasGameFinished()) {
            sc.nextLine();
            registerScoreboard();
        } else {
            if (option == 1) {
                controller.updateCurrentTurn();
            }
            inGameMenu();
        }
    }

    /** Records the winning player's score after finishing a match. Then print the score table of all registered players
     * Ask the winning player 3 characters to be registered in the score table. The score table is then displayed and prompted to clear the console on the controller. Finally, the game restarts
     */
    public void registerScoreboard() {
        System.out.print("\nPlease, enter 3 characters to be registered on the scoreboard: ");
        String name = sc.nextLine();
        if (name.length() == 3) {
            //Registering info in the scoreboard
            controller.registerScore(name);
            System.out.println("Score registered correctly.");
            System.out.println(controller.printScoreboard());
            //Resetting game
            controller.resetGame();
            //Cleaning console
            System.out.println("\nGame finished.\n\nPress enter key to continue...");
            sc.nextLine();
            controller.cleanConsole();
            showMenu();
        } else {
            System.out.println("Error. Try again.");
            registerScoreboard();
        }
    }
}