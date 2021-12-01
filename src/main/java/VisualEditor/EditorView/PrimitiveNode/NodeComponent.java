package main.java.VisualEditor.EditorView.PrimitiveNode;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public interface NodeComponent
{
    int getX();

    int getY();

    Point2D getCoords();

    int getHeight();

    int getWidth();

    Dimension2D getDimension();
}
