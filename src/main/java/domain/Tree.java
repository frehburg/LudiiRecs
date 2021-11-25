package main.java.domain;

import main.java.interfaces.iNode;
import main.java.interfaces.iTree;

import java.util.*;

public class Tree implements iTree {

    private final iNode root;

    public Tree(iNode root) {
        this.root = root;
    }

    /**
     * Retrieves the root of the tree
     *
     * @return
     */
    @Override
    public Object getRoot() {
        return root;
    }

    /**
     * Returns the tree in an preorder traversal
     *
     * @return
     */
    @Override
    public List<iNode> traversal() {
        return traversal(0);
    }

    /**
     * If
     * i = 0 : pre order
     * i = 1 : post order
     * @param i
     * @return
     */
    public List<iNode> traversal(int i) {
        switch(i) {
            case 1 : return postOrderTraversal(this.root);
            default: return preOrderTraversal(this.root);
        }
    }
    
    private List<iNode> preOrderTraversal(iNode r) {
        ArrayList<iNode> traversal = new ArrayList<>();
        traversal.add(r);
        for(iNode c : r.getChildren()) {
            traversal.addAll(preOrderTraversal(c));
        }
        return traversal;
    }


    private List<iNode> postOrderTraversal(iNode r) {
        ArrayList<iNode> traversal = new ArrayList<>();
        for(iNode c : r.getChildren()) {
            traversal.addAll(preOrderTraversal(c));
        }
        traversal.add(r);
        return traversal;
    }

    @Override
    public List<List<iNode>> layerTraversal() {
        ArrayList<List<iNode>> traversal = new ArrayList<>();
        ArrayList<iNode> layer = new ArrayList<>();
        ArrayList<iNode> newLayer;

        layer.add(this.root);
        while (!layer.isEmpty()) {
            traversal.add(layer);
            newLayer = new ArrayList<>();
            for (iNode p : layer) {
                newLayer.addAll(p.getChildren());
            }
            layer = newLayer;
        }
        traversal.remove(0);
        return traversal;
    }
}
