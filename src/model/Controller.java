package model;

public class Controller {
    private ScoreTree scores;
    private PlayerList players;
    private Board board;

    public Controller() {
        this.scores = new ScoreTree();
        this.players = new PlayerList();
    }

    /** It creates the board with the rows and columns specified by the user
     * It inserts the nodes to that board using the recursive method insertNodes
     * @param rows the number of rows of the board
     * @param columns the number of columns of the board
     */
    public void generateBoard(int rows, int columns) {
        this.board = new Board(rows, columns);
        insertNodes(1, rows*columns);
        board.addSnakes((int) (((rows * columns)/2) * 0.8));
        board.addLadders((int) (((rows * columns)/2) * 0.8));
    }

    /** Validates if the values entered to create the table correspond to a dimension equal to or greater than 4x4
     * Validates if the columns and rows entered are greater than 4
     * @param rows the number of rows of the board
     * @param columns the number of columns of the board
     */
    public boolean validateBoard(int rows, int columns) {
        if (rows > 3 && columns > 3) {
            generateBoard(rows, columns);
            return true;
        } else {
            return false;
        }
    }

    /** It calls the recursive function print slots of the current board
     * @return boardStr the board that is going to be shown to the user
     */
    public String showBoard() {
        return board.printSlots();
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

    /** Create a player and add him to the game
     * Receives the symbol that will represent the player in question and adds it to the game
     * @param symbol The chosen symbol that will represent the player
     * @return Returns if the player has been created
     */
    public String createPlayer(String symbol) {
        if (validateSymbol(symbol.charAt(0), 0)) {
            players.addPlayer(new Player(symbol.charAt(0)));
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
                if (players.validatePlayer(symbols.charAt(i)) == false) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return validateSymbol(symbol, ++i);
            }
        }
    }
}
