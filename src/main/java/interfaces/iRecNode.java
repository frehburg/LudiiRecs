package main.java.interfaces;

import main.java.Parsing.LudemeType;
import main.java.Parsing.PreLudemeType;

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
     * Returns the first classification of the ludeme
     * @return
     */
    PreLudemeType getPreLudemeType();

    void setPreLudemeType(PreLudemeType pre);

    /**
     * Returns the updated classification of the ludeme
     * @return
     */
    LudemeType getLudemeType();

    void setLudemeType(LudemeType type);
}
