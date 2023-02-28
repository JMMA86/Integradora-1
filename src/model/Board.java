package model;

public class Board {
    private Slot head;
    private Slot tail;
    private int numberOfColumns;
    private int numberOfRows;

    public Board(int rows, int columns) {
        this.head = null;
        this.tail = null;
        this.numberOfColumns = columns;
        this.numberOfRows = rows;
    }

    public void insertSlot(Slot newSlot) {
        if(head == null) {
            head = newSlot;
        } else {
            tail.setNext(newSlot);
            newSlot.setPrevious(tail);
        }
        tail = newSlot;
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

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
