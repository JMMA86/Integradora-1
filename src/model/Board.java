package model;

import java.util.Random;

public class Board {
    private Slot head;
    private Slot tail;

    private final Random random;
    private int numberOfColumns;
    private int numberOfRows;
    private final int totalSlots;

    public Board(int rows, int columns) {
        this.head = null;
        this.tail = null;
        this.numberOfColumns = columns;
        this.numberOfRows = rows;
        this.random = new Random();
        this.totalSlots = columns * rows;
    }

    public void insertSlot(Slot newSlot) {
        if (head == null) {
            head = newSlot;
        } else {
            tail.setNext(newSlot);
            newSlot.setPrevious(tail);
        }
        tail = newSlot;
    }

    public Slot searchSlotByValue(int value) {
        return searchSlotByValue(head, value);
    }

    public Slot searchSlotByValue(Slot pointer, int value) {
        if (pointer.getValue() != value) {
            pointer = searchSlotByValue((Slot) pointer.getNext(), value);
        }

        return pointer;
    }

    public void addSnakes() {
        int totalSnakes = random.nextInt(1, numberOfRows * numberOfColumns);

        addSnakes(totalSnakes, 0);
    }

    private void addSnakes(int totalSnakes, int iterator) {
        if (totalSnakes < iterator) return;

        int start = random.nextInt(totalSlots / numberOfRows, totalSlots); //It starts from total / rows to avoid row 1

        int startRow = findSlotColumn(start); //The row of the start of the snake

        Slot newSnake = searchSlotByValue(start);

        if (newSnake.getSteps() != 1) return;

        int lowestRange = head.getValue() + 1; // The lowest row except the start

        int maxRange = start - (start - (numberOfColumns * (startRow - 1))); // The higher row in which start is not included

        int end = random.nextInt(lowestRange, maxRange);

        int stepsBack = (start - end) * -1;

        if (searchSlotByValue(stepsBack).getSteps() != 1) return; //Making sure that the end isn't pointing to another slot

        newSnake.setSteps(stepsBack);

        addSnakes(totalSnakes, ++iterator);
    }

    /** This functions takes a value and return in which row is the value placed.
     * Initially it start with the lowest row (total - (rows * columns)) and will subtract
     * till the value is bigger than the actual subtract.
     *
     * @param value The value of the slot.
     * @return This function returns an int with the row of the value given to it.
     * */
    public int findSlotColumn(int value) {
        return findSlotColumn(value, numberOfColumns, 0);
    }

    private int findSlotColumn(int value, int iterator, int acu) {
        if (value >= totalSlots - (numberOfRows * iterator)) { // This must start in rows - 1 to check from the lowest row to the higher
            return findSlotColumn(value, --iterator, ++acu);
        }

        return acu;
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
