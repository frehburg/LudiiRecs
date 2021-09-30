package Tree;

import Parsing.LudemeType;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private List<Tree> children;
    private List<Double> weights;
    private final String kw;
    private int used;
    private final boolean isTerminal;
    private LudemeType type;

    public Tree(List<Tree> children, String kw, boolean isTerminal, LudemeType type) {
        this.type = type;
        this.children = children;
        if(children == null)
            children = new ArrayList<Tree>();
        this.kw = kw;
        this.isTerminal = isTerminal;
        weights = new ArrayList<>();
        used = 1;
    }

    public void addChild(List<Tree> children, String kw, boolean isTerminal, LudemeType type) {
        Tree child = new Tree(children, kw, isTerminal, type);
        boolean alreadyChild = false;
        one:for(int i = 0; i < children.size(); i++) {
            Tree c = children.get(i);
            if(c.getKw().equals(child.getKw())) {
                children.get(i).incrementUsed(); //if the child already exists then its used is incremented by 1
                alreadyChild = true;
                break one;
            }
        }
        if(!alreadyChild) {
            this.children.add(child);
        }
    }

    public void incrementUsed() {
        used++;
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

    public LudemeType getType() {
        return type;
    }
}
