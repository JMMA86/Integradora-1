package model;

public class Board {
    private Slot head;
    private Slot tail;

    public Board() {
        this.head = null;
        this.tail = null;
    }




    // GETTERS AND SETTERS

    public Slot getHead() {
        return head;
    }

    public void setHead(Slot head) {
        this.head = head;
    }

    public Slot getTail() {
        return tail;
    }

    public void setTail(Slot tail) {
        this.tail = tail;
    }

}
