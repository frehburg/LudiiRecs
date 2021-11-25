package main.java.interfaces;

import main.java.parser.LudemeType;

import java.util.List;

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
    List<Double> getWeights();

    /**
     * Returns an List of type iRecNode of the children
     * @return
     */
    List<iRecNode> getRecChildren();


    /**
     * Returns the updated classification of the ludeme
     * @return
     */
    LudemeType getLudemeType();

    void setLudemeType(LudemeType type);

    @Override
    /**
     * Adds a child to the children list
     * @param keyword
     */
    iRecNode addChild(String keyword) throws NullPointerException;
}
