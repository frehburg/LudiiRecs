package main.java.domain;

import main.java.Parsing.LudemeType;
import main.java.Parsing.PreLudemeType;
import main.java.interfaces.iNode;
import main.java.interfaces.iRecNode;

import java.util.ArrayList;

public class RecNode implements iRecNode {
    private static int counter = 0;
    /**
     * Given as a parent to every Node that has no parent
     */
    public static final iRecNode SUPER_NODE = new RecNode(null, new ArrayList<iNode>(), "SUPER_NODE", NodeType.SUPER);

    private iRecNode parent;
    private ArrayList<iRecNode> children;
    private String keyword;
    private NodeType nodeType;
    private int id;
    private int occurrenceFrequency;
    private PreLudemeType pre;
    private  LudemeType type;

    public RecNode(String keyword) {
        this.parent = SUPER_NODE;
        this.children = new ArrayList<iRecNode>();
        this.keyword = keyword;
        this.nodeType = NodeType.ROOT;
        this.id = counter++;
        occurrenceFrequency = 0;
        // TODO: Work on null check
        if(isNull(parent, children, keyword))
            throw new NullPointerException("The node you are trying to create is null");
        pre = PreLudemeType.ROOT;
        type = LudemeType.ROOT;

    }
    /**
     * TODO: CHANGE
     * @param o
     * @param iNodes
     * @param super_node
     * @param aSuper
     */
    public RecNode(Object o, ArrayList<iNode> iNodes, String super_node, NodeType aSuper) {
    }



    /**
     * Adds a child to the children list
     *
     * @param keyword
     */
    @Override
    public void addChild(String keyword) throws NullPointerException {
        iRecNode tmp = new RecNode(this, new ArrayList<iNode>(), keyword);
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
        return keyword;
    }

    /**
     * Returns the parent of the node if there exists one and throws a NullPointerException if there is none.
     *
     * @return
     */
    @Override
    public iNode getParent() {
        return parent;
    }

    /**
     * Returns the list of children
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
        return children.size();
    }

    /**
     * Returns the node type
     *
     * @return
     */
    @Override
    public NodeType getNodeType() {
        return this.getNodeType();
    }

    /**
     * Returns the id of the node
     *
     * @return
     */
    @Override
    public String getId() {
        return "RN"+id;
    }

    /**
     * Returns true, if the keyword is null, or x * " " (so also "")
     * or if the parent is null
     * or if the children list is null
     *
     * @param node
     * @return
     */
    @Override
    public boolean isNull(iNode node) {
        //TODO
        return false;
    }

    private boolean isNull(iRecNode parent, ArrayList<iRecNode> children, String keyword) {
        //TODO
        return false;
    }

    /**
     * Returns an exact copy of this node. Note that a clone is never equal to the original node, becuase they have
     * * different ids.
     *
     * @return
     */
    @Override
    public iNode clone() {
        //TODO: after constructors are written
        return null;
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
    public String toString() {
        String s = "(Node id: "+this.getId()+", keyword: "+this.getKeyword()+", occFrq: "+occurrenceFrequency+" parent: "+this.getParent().getKeyword()+", nodeType: "
                +this.getNodeType() + ", children: [";
        ArrayList<Double> weights = this.getWeights();
        for(int i = 0; i < this.getChildrenSize(); i++) {
            iRecNode c = children.get(i);
            double w = weights.get(i);
            s += c.getKeyword()+" w: "+w+", ";
        }
        s += "])";
        return s;
    }

    /**
     * Returns the occurence frequency of this node after it´s parent
     *
     * @return
     */
    @Override
    public int getOccurenceFrequency() {
        return occurrenceFrequency;
    }

    /**
     * Increments this nodes occurrence frequency in combination to its parent
     */
    @Override
    public void incrementOccurrenceFrequency() {
        occurrenceFrequency++;
    }

    /**
     * Returns the conditional probability of all of the children conditioned on the parents
     *
     * @return
     */
    @Override
    public ArrayList<Double> getWeights() {
        ArrayList<Double> weights = new ArrayList<>();
        double dSum = (double) occurrenceFrequency;
        for(iRecNode rn : children) {
            double w = ((double)rn.getOccurenceFrequency())/(dSum);
        }
        if(this.getChildrenSize() != weights.size()) {
            throw new IllegalStateException("The amount of children does not equal the amount of computed weights.");
        }
        return weights;
    }

    @Override
    public ArrayList<iRecNode> getRecChildren() {
        ArrayList<iRecNode> copy = new ArrayList<>();
        for(iRecNode n : this.children) {
            copy.add(n);
        }
        return copy;
    }

    /**
     * Returns the first classification of the ludeme
     *
     * @return
     */
    @Override
    public PreLudemeType getPreLudemeType() {
        return pre;
    }

    @Override
    public void setPreLudemeType(PreLudemeType pre) {
        this.pre = pre;
    }

    /**
     * Returns the updated classification of the ludeme
     *
     * @return
     */
    @Override
    public LudemeType getLudemeType() {
        return type;
    }

    @Override
    public void setLudemeType(LudemeType type) {
        this.type = type;
    }
}
