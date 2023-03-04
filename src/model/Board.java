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

    /** This method adds a new Slot to the current board in the last position.
     * @param newSlot the new Slot to be added
     */
    public void insertSlot(Slot newSlot) {
        if (head == null) {
            head = newSlot;
        } else {
            tail.setNext(newSlot);
            newSlot.setPrevious(tail);
        }
        tail = newSlot;
    }

    /** This function search a Slot based on the specified value
     * @param value the value of the slot to be searched
     * @return foundSlot whose value matches with the target value
     */
    public Slot searchSlotByValue(int value) {
        return searchSlotByValue(head, value);
    }

    public Slot searchSlotByValue(Slot pointer, int value) {
        if (pointer == null) {
            return null;
        }

        if (pointer.getValue() != value) {
            pointer = searchSlotByValue((Slot) pointer.getNext(), value);
        }

        return pointer;
    }

    /** This function adds the number of snakes that the user specified
     * @param totalSnakes The total number of snakes that are going to be added
     */
    public void addSnakes(int totalSnakes) {
        addSnakes(totalSnakes, 0);
    }

    private void addSnakes(int totalSnakes, int iterator) {
        if (totalSnakes < iterator) return;

        int start = random.nextInt(totalSlots / numberOfRows + 1, totalSlots); //It starts from total / rows to avoid row 1
        int startRow = findSlotRow(start); //The row of the start of the snake

        Slot newSnake = searchSlotByValue(start);

        if (newSnake.getSteps() != 1) return;

        int lowestRange = head.getValue() + 1; // The lowest row except the start
        int maxRange = start - (start - (numberOfColumns * (startRow - 1))); // The higher row in which start is not included
        int end = random.nextInt(lowestRange, maxRange);
        int stepsBack = (start - end) * -1;

        if (searchSlotByValue(end).getSteps() != 1) return; //Making sure that the end isn't pointing to another slot

        newSnake.setSteps(stepsBack);

        addSnakes(totalSnakes, ++iterator);
    }

    /** This function adds the number of ladders that the user specified
     * @param totalLadders The total number of ladders that are going to be added
     */
    //TODO make sure that every snake specified by the user is implemented
    public void addLadders(int totalLadders) {
        addLadders(totalLadders, 0);
    }

    private void addLadders(int totalLadders, int iterator) {
        if (totalLadders < iterator) return;

        int start = random.nextInt(head.getValue() + 1, totalSlots - numberOfColumns);
        int startRow = findSlotRow(start); //The row of the start of the ladder

        Slot newLadder = searchSlotByValue(start);

        if (newLadder.getSteps() != 1) return;

        int lowestRange = numberOfColumns * startRow + 1;
        int end = random.nextInt(lowestRange, totalSlots);
        int stepsForward = (start - end);

        if (searchSlotByValue(end).getSteps() != 1) return;

        newLadder.setSteps(stepsForward);

        addLadders(totalLadders, ++iterator);
    }


    /**
     * This function prints the slots of the board, based on the matrix structure.
     * It accumulates all the rows in the variable rowStr and add it to the board every time it is in a slot that divides exactly the number of rows
     * @return boardStr the board that is going to be showed to the user
     */
    public String printSlots() {
        return printSlots(tail, "", "", false);
    }

    private String printSlots(Slot current, String boardStr, String rowStr, boolean snakesLadders) {
        if(current == null || current.getValue() % numberOfColumns == 0) {
            boardStr += " \n";
            boardStr += rowStr;
            rowStr = "";
        }
        if(current == null) return boardStr; // base case

        int currentRow = (int)Math.ceil((double)current.getValue()/numberOfColumns);

        String slotValue;
        if(snakesLadders) {
            slotValue = " [null] ";
        } else {
            slotValue = " [" + current.getValue() + "] ";
        }
        if(currentRow % 2 == 0) {
            rowStr = rowStr + " " + slotValue;
        } else {
            rowStr = slotValue + " " + rowStr;
        }
        return printSlots((Slot)current.getPrevious(), boardStr, rowStr, snakesLadders); // recursive call
    }


    /** This functions takes a value and return in which row is the value placed.
     * Initially it start with the lowest row (total - (rows * columns)) and will subtract
     * till the value is bigger than the actual subtract.
     *
     * @param value The value of the slot.
     * @return This function returns an int with the row of the value given to it.
     * */
    public int findSlotRow(int value) {
        return findSlotRow(value, 1);
    }

    public int findSlotRow(int value, int iterator) {
        if (value > numberOfColumns * iterator) { // This must start in rows - 1 to check from the lowest row to the higher
            return findSlotRow(value, ++iterator);
        }

        return iterator;
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
