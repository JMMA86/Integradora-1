package model;

public class PlayerList {
    private Player head;

    public PlayerList() {
        this.head = null;
    }

    public void addPlayer(Player node) {
        if (head == null) {
            this.head = node;
            node.setNext(node);
        } else {
            addPlayer(node, head);
        }
    }

    public void addPlayer(Player node, Player current) {
        if (current.getNext() == head) {
            current.setNext(node);
            current.getNext().setNext(head);
        } else {
            addPlayer(node, (Player) current.getNext());
        }
    }

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
