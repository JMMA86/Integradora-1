package model;

import java.util.Random;

public class Controller {
    private ScoreTree scores;
    private PlayerList players;
    private Player currentPlayer;
    private Board board;
    private Random rnd;

    public Controller() {
        this.scores = new ScoreTree();
        this.players = new PlayerList();
        this.currentPlayer = null;
        this.rnd = new Random();
    }

    /** It creates the board with the rows and columns specified by the user
     * It inserts the nodes to that board using the recursive method insertNodes
     * @param rows the number of rows of the board
     * @param columns the number of columns of the board
     */
    public void generateBoard(int rows, int columns, int snakes, int ladders) {
        this.board = new Board(rows, columns);
        insertNodes(1, rows*columns);
        board.addSnakes(snakes);
        board.addLadders(ladders);
    }

    /** Validates if the values entered to create the table correspond to a dimension equal to or greater than 4x4
     * Validates if the columns and rows entered are greater than 4
     * @param rows the number of rows of the board
     * @param columns the number of columns of the board
     */
    public boolean validateBoard(int rows, int columns, int snakes, int ladders) {
        if (rows > 3 && columns > 3) {
            generateBoard(rows, columns, snakes, ladders);
            return true;
        } else {
            return false;
        }
    }

    /** It calls the recursive function print slots of the current board
     * @return boardStr the board that is going to be shown to the user
     */
    public String showBoard() {
        return board.printSlots(false);
    }

    /** It creates the number of slots that is indicated, from the initial value of current, to the limit value
     * @param current the current value for the new Node
     * @param limit the value of the last node to be created
     */

    public void insertNodes(int current, int limit) {
        if(current > limit) return;
        Slot newSlot = new Slot(current);
        board.insertSlot(newSlot);
        insertNodes(++current, limit);
    }


    /** It calls the recursive method print slots, including the snakes and ladders.
     * @return boardStr the board that is going to be shown to the user, it includes the snakes and ladders
     */
    public String showSnakesAndLadders() {
        return board.printSlots(true);
    }

    /** Create a player and add him to the game
     * Receives the symbol that will represent the player in question and adds it to the game
     * @param symbol The chosen symbol that will represent the player
     * @return Returns if the player has been created
     */
    public String createPlayer(String symbol) {
        if (validateSymbol(symbol.charAt(0), 0)) {
            Player newPlayer = new Player(symbol.charAt(0));
            newPlayer.setCurrentSlot(board.getHead());
            players.addPlayer(newPlayer);
            return "Created player.";
        } else {
            return "Invalid option.";
        }
    }

    /** Validates if the chosen symbol is valid or has not already been chosen by another player
     * Validates if the chosen symbol is valid or the nodes of each player already created do not contain it
     * @param symbol The chosen symbol that will represent the player
     * @param i Iterator that goes through the String of valid symbols to compare it with the chosen one
     * @return Returns if the symbol is valid
     */
    public boolean validateSymbol(char symbol, int i) {
        //Put valid symbols here
        String symbols = "*!OX%$#+&";
        if (i == 9) {
            return false;
        } else {
            //Validate if the symbol is correct
            if (symbols.charAt(i) == symbol) {
                //Validate if the symbol has been chosen
                return !players.validatePlayer(symbols.charAt(i));
            } else {
                return validateSymbol(symbol, ++i);
            }
        }
    }

    public boolean rollDice() {
        if(currentPlayer == null) currentPlayer = players.getHead();
        int steps = rnd.nextInt(1, 7);
        Slot newPosition = movePlayer(currentPlayer.getCurrentSlot(), steps);
        if(newPosition == board.getTail()) return true;
        currentPlayer.setCurrentSlot(newPosition);
        currentPlayer = (Player)currentPlayer.getNext();
        return false;
    }

    private Slot movePlayer(Slot slot, int steps) {
        if(slot == board.getTail()) return slot; // win game
        if(steps == 0) { // finished initial movement
            if(slot.getLinkId() == null) { // regular slot
                return slot;
            } else { // snake or ladders
                return movePlayer(slot, slot.getSteps());
            }
        }
        if(steps < 0) {
            return movePlayer((Slot)slot.getPrevious(), ++steps);
        } else {
            return movePlayer((Slot)slot.getNext(), --steps); // going forward
        }
    }

}
