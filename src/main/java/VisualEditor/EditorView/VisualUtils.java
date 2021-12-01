package main.java.VisualEditor.EditorView;

import java.awt.*;
import java.awt.geom.Point2D;

public final class VisualUtils
{

    public static Point2D getPointCentered(Point2D A, int HEIGHT, int WIDTH)
    {
        return new Point((int)(A.getX() + HEIGHT/2), (int)(A.getY() + WIDTH/2));
    }

}
