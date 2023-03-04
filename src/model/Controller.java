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
        if (rows > 3 && columns > 3) {
            generateBoard(rows, columns);
            return true;
        } else {
            return false;
        }
    }

    public void generateBoard(int rows, int columns) {
        this.board = new Board(rows, columns);
        insertNodes(1, rows*columns);
        //board.addSnakes();
    }
    public void insertNodes(int current, int limit) {
        if(current > limit) return;
        Slot newSlot = new Slot(current);
        board.insertSlot(newSlot);
        insertNodes(++current, limit);
    }

    public String createPlayer(String symbol) {
        if (validateSymbol(symbol.charAt(0), 0) == true) {
            players.addPlayer(new Player(symbol.charAt(0)));
            return "Created player.";
        } else {
            return "Invalid option.";
        }
    }

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
