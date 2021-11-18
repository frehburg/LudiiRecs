package main.java.interfaces;

import java.util.ArrayList;

public interface iRecNode extends iNode{
    /**
     * Returns the occurence frequency of this node after it´s parent
     * @return
     */
    int getOccurenceFrequency();

    /**
     * Increments this nodes occurrence frequency in combination to its parent
     */
    void incrementOccurrenceFrequency();

    /**
     * Returns the conditional probability of all of the children conditioned on the parents
     * @return
     */
    ArrayList<Double> getWeights();

    ArrayList<iRecNode> getRecChildren();
}
