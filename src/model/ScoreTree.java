package model;
import java.text.DecimalFormat;

public class ScoreTree {
    private Score root;
    private DecimalFormat formato;

    public ScoreTree() {
        this.root = null;
        this.formato = new DecimalFormat("#.##");
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

    public String inOrderString(){
        return inOrderString(root);
    }

    private String inOrderString(Score current){
        if(current == null){
            return "";
        }

        return inOrderString(current.getRight()) + "\n[" + current.getName() + " " + formato.format(current.getValue()) + "]" + inOrderString(current.getLeft());
    }

    public Score getRoot() {
        return root;
    }

    public void setRoot(Score root) {
        this.root = root;
    }
}
