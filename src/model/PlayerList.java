package model;

public class PlayerList {
    private Player head;

    public PlayerList() {
        this.head = null;
    }

    /** Adds a player to the game
     * Adds a new player node to the linked list of players. If the linked list already has its head occupied, call the recursive method to add the player
     * @param node Player node to create
     */
    public void addPlayer(Player node) {
        if (head == null) {
            this.head = node;
            node.setNext(node);
        } else {
            addPlayer(node, head);
        }
    }

    /** Adds a player to the game
     * Recursive method that adds a new player node to the linked list of players
     * @param node
     * @param current
     */
    public void addPlayer(Player node, Player current) {
        if (current.getNext() == head) {
            current.setNext(node);
            current.getNext().setNext(head);
        } else {
            addPlayer(node, (Player) current.getNext());
        }
    }

    /** Validates if a symbol is already being used by a player in the game
     * Initializes the recursive method that loops through the nodes of the linked list of players to check if any are using a given symbol
     * @param symbol Symbol to validate
     * @return Returns whether the symbol is being used or not
     */
    public boolean validatePlayer(char symbol) {
        return validatePlayer(symbol, head, 0);
    }

    public boolean validatePlayer(char symbol, Player current, int i) {
        if (head == null) {
            return false;
        }
        if (i == 3) {
            return false;
        }
        if (current.getId() == symbol) {
            return true;
        } else {
            return validatePlayer(symbol, (Player) current.getNext(), ++i);
        }
    }

    public Player getHead() {
        return head;
    }

    public void setHead(Player head) {
        this.head = head;
    }
}
