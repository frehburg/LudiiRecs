package main.java.VisualEditor.EditorView.Node;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

/**
 * Interface for the graphical node
 * @author nic0gin
 */
public interface VisualNode
{
    int x = 0;


    /**
     *
     * @return x coordinate of the node
     */
    int getX();

    /**
     *
     * @return y coordinate of the node
     */
    int getY();

    /**
     *
     * @return coordinates of the node
     */
    Point2D getCoords();

    /**
     *
     * @return height of the node
     */
    int getHeight();

    /**
     *
     * @return width of the node
     */
    int getWidth();

    /**
     *
     * @return size parameters of the node
     */
    Dimension2D getDimension();

    /**
     * draws nodes to the graphics base component; to be applied inside paint() method of JComponent
     * @param g graphics base
     */
    void drawNode(Graphics g);
}
