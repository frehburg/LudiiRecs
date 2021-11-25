package main.java.domain;


import main.java.interfaces.iNode;

import java.util.ArrayList;

/**
 * To create a structure:
 * -use the constructor to create the first node
 * -use the addChild method to create all children. DO NOT use the constructor again
 */
public class Node implements iNode {
    private static int counter = 0;
    /**
     * Given as a parent to every Node that has no parent
     */
    public static final iNode SUPER_NODE = new Node(null, new ArrayList<iNode>(), "SUPER_NODE", NodeType.SUPER);

    private iNode parent;
    private ArrayList<iNode> children;
    private String keyword;
    private NodeType nodeType;
    private final int id;
    private String implementation;
    private ArrayList<String> constructors;

    public Node(String keyword) {
        this.parent = SUPER_NODE;
        this.children = new ArrayList<iNode>();
        if(isNull(parent, children, keyword))
            throw new NullPointerException("The node you are trying to create is null");
        this.keyword = keyword;
        this.nodeType = NodeType.ROOT;
        this.id = counter++;
        this.implementation = "";
        this.constructors = new ArrayList<>();
    }

    /**
     * Constructor for addChild() method
     * @param parent
     * @param children
     * @param keyword
     */
    private Node(iNode parent, ArrayList<iNode> children, String keyword) {
        this.parent = parent;
        this.children = children;
        this.keyword = keyword;
        if(isNull(parent, children, keyword)) { //null check
            throw new NullPointerException("The node you have passed along is null");
        }
        if(children.isEmpty()) {
            this.nodeType = NodeType.LEAF;
        } else {
            this.nodeType = NodeType.INNER;
        }
        if(parent.equals(SUPER_NODE)) {
            this.nodeType = NodeType.ROOT;
        }
        this.id = counter++;
        this.implementation = "";
        this.constructors = new ArrayList<>();
    }

    /**
     * Creates super node
     * @param parent
     * @param children
     * @param keyword
     * @param nodeType
     */
    private Node(iNode parent, ArrayList<iNode> children, String keyword, NodeType nodeType) {
        this.parent = parent;
        this.children = children;
        this.keyword = keyword;
        this.nodeType = NodeType.SUPER;
        this.id = counter++;
        this.implementation = "";
        this.constructors = new ArrayList<>();
    }

    /**
     * Adds a child to the children ArrayList, by creating a new one
     * Null Check
     *
     * @param keyword
     */
    @Override
    public iNode addChild(String keyword) throws NullPointerException {
        iNode tmp = new Node(this, new ArrayList<iNode>(), keyword);
        this.children.add(tmp);
        if(this.nodeType != NodeType.ROOT)
            this.nodeType = NodeType.INNER;
        return this.clone();
    }

    /**
     * Returns the ludeme that this node represents
     *
     * @return
     */
    @Override
    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Returns the variant of  implementation in the syntax
     *
     * @return
     */
    @Override
    public String getImplementation() {
        return null;
    }

    /**
     * Returns a ArrayList of all possible variants of implementation in the syntax
     *
     * @return
     */
    @Override
    public ArrayList<String> getConstructors() {
        return null;
    }

    /**
     * Returns the parent of the node if there exists one and throws a NullPointerException if there is none.
     *
     * @return
     */
    @Override
    public iNode getParent() {
        return this.parent;
    }

    /**
     * Returns a copy of the ArrayList of children, containing all actual children. But this way the ArrayList of children
     * cannot be altered from outside.
     *
     * @return
     */
    @Override
    public ArrayList<iNode> getChildren() {
        ArrayList<iNode> copy = new ArrayList<>();
        for(iNode n : this.children) {
            copy.add(n);
        }
        return copy;
    }

    /**
     * Returns the ArrayList of siblings (nodes with the same parent
     *
     * @return
     */
    @Override
    public ArrayList<iNode> getSiblings() {
        return (ArrayList<iNode>) getParent().getChildren();
    }

    @Override
    public int getChildrenSize() {
        return this.children.size();
    }

    /**
     * Returns the node type
     *
     * @return
     */
    @Override
    public NodeType getNodeType() {
        return this.nodeType;
    }

    /**
     * Returns true, if the keyword is null, or x * " " (so also "")
     * or if the parent is null
     * or if the children ArrayList is null
     * @param node
     * @return
     */
    public boolean isNull(iNode node) {
        if(node == null)
            return true;
        return isNull(node.getParent(), (ArrayList<iNode>) node.getChildren(), node.getKeyword());
    }

    /**
     * Returns true, if the keyword is null, or x * " " (so also "")
     * or if the parent is null
     * or if the children ArrayList is null
     * @param parent
     * @param children
     * @param keyword
     * @return
     */
    private boolean isNull(iNode parent, ArrayList<iNode> children, String keyword) {
        if(parent == null)
            return true;
        if(children == null)
            return true;
        if(keyword == null || keyword == "")
            return true;
        for(int i = 0; i < keyword.length(); i++) {
            if(keyword.charAt(i) == ' ') {
                if(i == keyword.length() - 1) { // if all characters are spaces then it is an empty keyword
                    return true;
                }
            } else { // if it is not a space, then it is fine
                break;
            }
        }
        return false;
    }

    /**
     * Returns an exact copy of this node. Note that a clone is never equal to the original node, becuase they have
     * different ids.
     * @return
     */
    public iNode clone() {
        return new Node(this.parent, this.children, this.keyword);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof iNode) {
            iNode node = (iNode) o;
            if(this.getId().equals(node.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getId() {
        return "N"+id;
    }

    /**
     * Sets the variant of  implementation in the syntax to a new value implementation
     *
     * @param implementation
     * @return
     */
    @Override
    public void setImplementation(String implementation) {

    }

    @Override
    public String toString() {
        String s = "(Node id: "+this.getId()+", keyword: "+this.getKeyword()+", parent: "+this.getParent().getKeyword()+", nodeType: "
                +this.getNodeType() + ", children: [";
        for(iNode c : children) {
            s += c.getKeyword()+", ";
        }
        s += "])";
        return s;
    }
}
