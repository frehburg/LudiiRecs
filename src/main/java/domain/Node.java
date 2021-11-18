package main.java.domain;


import main.java.interfaces.iNode;

import java.util.ArrayList;

/**
 * To create a structure:
 * -use the constructor to create the first node
 * -use the addChild method to create all children. DO NOT use the constructor again
 */
public class Node implements iNode {
    protected static int counter = 0;
    /**
     * Given as a parent to every Node that has no parent
     */
    public static final iNode SUPER_NODE = new Node(null, new ArrayList<iNode>(), "SUPER_NODE", NodeType.SUPER);

    protected iNode parent;
    protected ArrayList<iNode> children;
    protected String keyword;
    protected NodeType nodeType;
    protected int id;

    public Node(String keyword) {
        this.parent = SUPER_NODE;
        this.children = new ArrayList<iNode>();
        if(isNull(parent, children, keyword))
            throw new NullPointerException("The node you are trying to create is null");
        this.keyword = keyword;
        this.nodeType = NodeType.ROOT;
        this.id = counter++;

    }

    protected Node(iNode parent, ArrayList<iNode> children, String keyword) {
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

    }

    /**
     * Creates super node
     * @param parent
     * @param children
     * @param keyword
     * @param nodeType
     */
    protected Node(iNode parent, ArrayList<iNode> children, String keyword, NodeType nodeType) {
        this.parent = parent;
        this.children = children;
        this.keyword = keyword;
        this.nodeType = NodeType.SUPER;
        this.id = counter++;
    }

    /**
     * Adds a child to the children list, by creating a new one
     * Null Check
     *
     * @param keyword
     */
    @Override
    public void addChild(String keyword) throws NullPointerException {
        iNode tmp = new Node(this, new ArrayList<iNode>(), keyword);
        this.children.add(tmp);
        if(this.nodeType != NodeType.ROOT)
            this.nodeType = NodeType.INNER;
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
     * Returns the parent of the node if there exists one and throws a NullPointerException if there is none.
     *
     * @return
     */
    @Override
    public iNode getParent() throws NullPointerException {
        return this.parent;
    }

    /**
     * Returns a copy of the list of children, containing all actual children. But this way the list of children
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
     * or if the children list is null
     * @param node
     * @return
     */
    public boolean isNull(iNode node) {
        if(node == null)
            return true;
        return isNull(node.getParent(), node.getChildren(), node.getKeyword());
    }

    /**
     * Returns true, if the keyword is null, or x * " " (so also "")
     * or if the parent is null
     * or if the children list is null
     * @param parent
     * @param children
     * @param keyword
     * @return
     */
    protected boolean isNull(iNode parent, ArrayList<iNode> children, String keyword) {
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
            if(this.id == node.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getId() {
        return id;
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
