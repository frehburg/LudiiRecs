package main.java.interfaces;

import java.util.ArrayList;

public interface iVisualizableNode extends iNode{
    /**
     * Returns the implementation that is currently used, parsed from ludii-grammar-X.X.XX.txt
     * @return
     */
    String getCurImplementation();

    /**
     * Returns all implementation of this ludeme, parsed from ludii-grammar-X.X.XX.txt
     * @return
     */
    ArrayList<String> getAllImplementations();

}
