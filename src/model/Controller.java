package model;

public class Controller {
    private ScoreTree scores;
    private PlayerList players;
    private Board board;

    public Controller() {
        this.scores = new ScoreTree();
        this.players = new PlayerList();
    }

    public boolean validateBoard(int rows, int columns) {
        if (rows > 3 && rows > 3) {
            generateBoard(rows, columns);
            return true;
        } else {
            return false;
        }
    }

    public void generateBoard(int rows, int columns) {
        this.board = new Board(rows, columns);
        insertNodes(1, rows*columns);
        board.addSnakes();
    }
    public void insertNodes(int current, int limit) {
        if(current > limit) return;
        Slot newSlot = new Slot(current);
        board.insertSlot(newSlot);
        insertNodes(++current, limit);
    }

    public void createPlayer(char symbol) {
        players.addPlayer(symbol);
    }

}
