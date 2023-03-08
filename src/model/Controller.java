package model;

import java.util.Random;
import java.text.DecimalFormat;

public class Controller {
    private ScoreTree scores;
    private PlayerList players;
    private Player currentPlayer;
    private int currentTurn;
    private Board board;
    private Thread t;
    private boolean running;
    private double seconds;
    private Random rnd;
    boolean finishedGame;

    public Controller() {
        this.scores = new ScoreTree();
        this.players = new PlayerList();
        this.running = false;
        this.currentPlayer = null;
        this.rnd = new Random();
        this.finishedGame = false;
        this.currentTurn = 1;
    }

    public void startTimer() {
        this.seconds = 600;
        running = true;
        t = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(10);
                    seconds-=0.010;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
    }

    public void stopTimer() {
        running = false;
        t.interrupt();
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

    /** Validates if the values entered to create the table correspond to a dimension equal to or greater than 4x4. Also, snakes and ladders can´t exceed 40% of the board
     * Validates if the columns and rows entered are greater than 4
     * @param rows the number of rows of the board
     * @param columns the number of columns of the board
     */
    public boolean validateBoard(int rows, int columns, int snakes, int ladders) {
        if (rows > 3 && columns > 3 && ladders + snakes <= rows * columns * 0.4) {
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
        return printSlots(false);
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
        return printSlots(true);
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void updateCurrentTurn() {
        if (currentTurn == 3) {
            this.currentTurn = 1;
        } else {
            this.currentTurn++;
        }
    }

    /**
     * This function prints the slots of the board, based on the matrix structure.
     * It accumulates all the rows in the variable rowStr and add it to the board every time it is in a slot that divides exactly the number of rows
     * @return boardStr the board that is going to be showed to the user
     */
    public String printSlots(boolean snakesLadders) {
        return printSlots(board.getTail(), "", "", snakesLadders);
    }

    private String printSlots(Slot current, String boardStr, String rowStr, boolean snakesLadders) {
        if(current == null || current.getValue() % board.getNumberOfColumns() == 0) {
            boardStr += " \n";
            boardStr += rowStr;
            rowStr = "";
        }
        if(current == null) return "\n\n" + ( (snakesLadders) ? "Snakes and Ladders:" : "Current Board:" ) + boardStr; // base case

        int currentRow = (int)Math.ceil((double)current.getValue()/board.getNumberOfColumns());

        String slotValue = "[ ";
        if(snakesLadders) {
            slotValue += current.getLinkId() != null ? current.getLinkId() : "-";
        } else {
            String slotPlayers = getSlotPlayers(current, players.getHead(), false);
            slotValue += String.format("%-4s", current.getValue()) + String.format("%-3s", slotPlayers);
        }
        slotValue += " ]";
        if(currentRow % 2 == 0) {
            if(rowStr.equals("")) {
                rowStr += slotValue;
            } else {
                rowStr = rowStr + " " + slotValue;
            }
        } else {
            rowStr = slotValue + " " + rowStr;
        }
        return printSlots((Slot)current.getPrevious(), boardStr, rowStr, snakesLadders); // recursive call
    }

    private String getSlotPlayers(Slot current, Player player, boolean passed) {
        if(player == players.getHead()) {
            if(!passed) {
                passed = true;
            } else {
                return "";
            }
        }
        if(player.getCurrentSlot() == current) {
            return player.getId() + getSlotPlayers(current, (Player)player.getNext(), passed);
        } else {
            return getSlotPlayers(current, (Player)player.getNext(), passed);
        }
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

    /** It gets a new random number from 1 to 6, and sets the new position to the current player.
     * @return gameStatus it is true only when a player gets to the last position, that is, when someone wins the game
     */
    public String rollDice() {
        // calculating movement
        if(currentPlayer == null) currentPlayer = players.getHead();
        int steps = rnd.nextInt(1, 7);
        Slot newPosition = movePlayer(currentPlayer.getCurrentSlot(), steps);

        // Generating output message
        String status = "\nRoll Dice Summary:";
        status += String.format("\n - Player '%s' moved %d steps", currentPlayer.getId(), steps);
        int initialPosition = currentPlayer.getCurrentSlot().getValue();
        int delta = newPosition.getValue() - initialPosition;
        if(delta != steps) {
            if(newPosition != board.getTail()) {
                status += String.format("\n - Player found a %s at %d position", (delta < steps) ? ("snake") : ("ladder"), initialPosition+steps);
            }
        } else {
            status += "\n - Player did not found any snake or ladder";
        }
        if(newPosition != board.getTail()) {
            status += String.format("\n - The new position for player '%s' is %d", currentPlayer.getId(), newPosition.getValue());
        } else {
            if(newPosition == board.getTail()) finishedGame = true;
            stopTimer();
            DecimalFormat formato = new DecimalFormat("#.##");
            double score = this.seconds / 6;
            status += String.format("\n - Player '%s' won the game!", currentPlayer.getId()) + "\nScore: " + formato.format(score);
        }

        // updating positions

        currentPlayer.setCurrentSlot(newPosition);
        currentPlayer = (Player)currentPlayer.getNext();
        return status;
    }


    /** This method gets the new position, given a current slot and a number of steps either forward or backward
     * @param slot The initial position
     * @param steps The number of steps to move, if positive it moves forward, otherwise, backward.
     * @return newPosition The new slot to be assigned to the player
     */
    private Slot movePlayer(Slot slot, int steps) {
        if(slot == board.getHead() && steps <= 0) return slot;
        if(slot == board.getTail()) return slot; // stop forced
        if(steps == 0) { // finished initial movement
            if(slot.getLinkId() == null) { // regular slot
                return slot;
            } else { // snake or ladders
                if(slot.getSteps() == 0) {
                    return slot;
                } else {
                    return movePlayer(slot, slot.getSteps());
                }
            }
        }
        if(steps < 0) {
            return movePlayer((Slot)slot.getPrevious(), ++steps);
        } else {
            return movePlayer((Slot)slot.getNext(), --steps); // going forward
        }
    }

    public char getCurrentSymbol() {
        if (currentPlayer == null) {
            return players.getHead().getId();
        } else {
            return currentPlayer.getId();
        }
    }

    public boolean hasGameFinished() {
        return finishedGame;
    }

    public void registerScore(String name) {
        scores.add(new Score(name, this.seconds/6));

    }

    public String printScoreboard() {
        return "Current scoreboard:\n" + "[COD] " + "[SCR]" + scores.inOrderString();
    }

    public void resetPlayers() {
        this.players = new PlayerList();
        this.running = false;
        this.currentPlayer = null;
        this.finishedGame = false;
        this.currentTurn = 1;
    }

}
