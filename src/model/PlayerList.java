package model;

public class PlayerList {
    private Player head;

    public PlayerList() {
        this.head = null;
    }

    public void addPlayer(char symbol) {
        Player node = new Player(symbol);
        if (head == null) {
            this.head = node;
            node.setNext(node);
        } else {

        }
    }

    public Player getHead() {
        return head;
    }

    public void setHead(Player head) {
        this.head = head;
    }
}
