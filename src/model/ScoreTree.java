package model;

public class ScoreTree {
    private Score root;

    public ScoreTree() {
        this.root = null;
    }

    public void add(Score node) {
        if (root == null) {
            root = node;
        } else {
            add(node, root);
        }
    }

    public void add(Score node, Score current) {
        if (node.getValue() < current.getValue()) {
            if(current.getLeft() == null){
                current.setLeft(node);
            } else {
                add(node, current.getLeft());
            }
        } else if (node.getValue() > current.getValue()) {
            if (current.getRight() == null) {
                current.setRight(node);
            } else {
                add(node, current.getRight());
            }
        }
    }

    public Score getRoot() {
        return root;
    }

    public void setRoot(Score root) {
        this.root = root;
    }
}
