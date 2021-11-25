package main.java.interfaces;

import java.util.List;

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
    List<T> traversal();

    /**
     * Returns nodes in lists of layers
     * @return
     */
    List<List<iNode>> layerTraversal();
}
