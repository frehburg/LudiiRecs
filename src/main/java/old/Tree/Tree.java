package main.java.old.Tree;

import main.java.old.Parsing.PreLudemeType;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private List<Tree> children;
    private List<Double> weights;
    private final String kw;
    private int used;
    private final boolean isTerminal;
    // TODO: Change back to LudemeType when ready
    private PreLudemeType type;
    //TODO: add preferred: count * at the end of the keyword

    public Tree(List<Tree> children, String kw, boolean isTerminal, /*TODO: Change back to LudemeType when ready*/PreLudemeType type) {
        this.type = type;
        this.children = children;
        if(children == null)
            children = new ArrayList<Tree>();
        this.kw = kw;
        this.isTerminal = isTerminal;
        weights = new ArrayList<>();
        used = 1;
    }

    public void addChild(List<Tree> children, String kw, boolean isTerminal, /*TODO: Change back to LudemeType when ready*/PreLudemeType type) {
        Tree child = new Tree(children, kw, isTerminal, type);
        boolean alreadyChild = false;
        one:for(int i = 0; i < children.size(); i++) {
            Tree c = children.get(i);
            if(c == null)
                break;
            if(c.getKw().equals(child.getKw())) {
                children.get(i).incrementUsed(); //if the child already exists then its used is incremented by 1
                alreadyChild = true;
                break one;
            }
        }
        if(!alreadyChild) {
            if(this.children == null)
                this.children = new ArrayList<Tree>();
            this.children.add(child);
        }
    }

    public void incrementUsed() {
        used++;
    }

    public void addChild(Tree buildTree) {
        System.out.println(buildTree.toString());
        System.out.println(buildTree.children);
        addChild(buildTree.getChildren(), buildTree.getKw(), buildTree.isTerminal(), buildTree.getType());
    }

    //--------------------------------------------------------------
    public List<Tree> getChildren() {
        return children;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public String getKw() {
        return kw;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public int getUsed() {
        return used;
    }
    /*TODO: Change back to LudemeType when ready*/
    public /*TODO: Change back to LudemeType when ready*/PreLudemeType getType() {
        return type;
    }

    public String toString() {
        if(isTerminal) {
            return kw;
        } else {
            if(children.isEmpty())
                return kw+"";
            String s = kw+"{";
            for(Tree c : children) {
                if(c != null)
                    s += c.toString() + " ";
            }
            return s.substring(0,s.length()-1)+"}";
        }
    }


}
