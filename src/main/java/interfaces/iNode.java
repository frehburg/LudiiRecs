package main.java.interfaces;

import main.java.domain.NodeType;

import java.util.ArrayList;

public interface iNode {

    /**
     * Adds a child to the children list
     * @param keyword
     */
    void addChild(String keyword) throws NullPointerException;

    /**
     * Returns the ludeme that this node represents
     * @return
     */
    String getKeyword();

    /**
     * Returns the parent of the node if there exists one and throws a NullPointerException if there is none.
     * @return
     */
    iNode getParent() throws NullPointerException;

    /**
     * Returns the list of children
     * @return
     */
    ArrayList<iNode> getChildren();

    int getChildrenSize();

    /**
     * Returns the node type
     * @return
     */
    NodeType getNodeType();

    /**
     * Returns the id of the node
     * @return
     */
    int getId();

    /**
     * Returns true, if the keyword is null, or x * " " (so also "")
     * or if the parent is null
     * or if the children list is null
     * @param node
     * @return
     */
    public boolean isNull(iNode node);

    /**
     * Returns an exact copy of this node. Note that a clone is never equal to the original node, becuase they have
     *      * different ids.
     * @return
     */
    public iNode clone();

    public boolean equals(Object o);

    public String toString();
}
