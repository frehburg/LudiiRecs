package main.java.interfaces;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public interface iGraph<T>
{
    /**
     * Retrieves the root of the graph
     * @return
     */
    T getRoot();

    /**
     *
     * @return
     */
    HashMap<Integer, List<Integer>> getAdjacencyVertexList();

    /**
     *
     * @return
     */
    List<List<Integer>> getAdjacencyEdgeList();

    /**
     *
     * @return
     */
    int[][] getAdjacencyMatrix();

    /**
     *
     * @param id
     * @return
     */
    int getVertexX(int id);

    /**
     *
     * @param id
     * @return
     */
    int getVertexY(int id);

    /**
     *
     * @param id
     * @return
     */
    Point2D getVertexCoords(int id);

    /**
     *
     * @param id
     * @return
     */
    void setVertexX(int id, int x);

    /**
     *
     * @param id
     * @param y
     */
    void setVertexY(int id, int y);

    /**
     *
     * @param id
     * @param coords
     */
    void setVertexCoords(int id, Point2D coords);


}
