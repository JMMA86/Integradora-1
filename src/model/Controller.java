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
        board.addSnakes((rows * columns)/2);
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

    /** It calls the recursive function print slots of the current board
     * @return boardStr the board that is going to be shown to the user
     */
    public String showBoard() {
        return board.printSlots();
    }

}
