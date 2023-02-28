package model;

public class Node {
    private Node next;
    private Node previous;

    public Node() {
        this.next = null;
        this.previous = null;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}
