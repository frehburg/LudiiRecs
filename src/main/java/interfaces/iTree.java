package main.java.interfaces;

import java.util.ArrayList;

public interface iTree<T> {
    /**
     * Retrieves the root of the tree
     * @return
     */
    T getRoot();

    /**
     * Returns the tree in an inorder traversal
     * @return
     */
    ArrayList<T> traversal();
}
