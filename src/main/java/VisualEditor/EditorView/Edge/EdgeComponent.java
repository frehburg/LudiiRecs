package main.java.VisualEditor.EditorView.Edge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class EdgeComponent extends JComponent
{

    private Point A, B;

    public EdgeComponent(Point A, Point B)
    {
        this.A = A;
        this.B = B;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        drawEdge(g2D);
    }

    public void drawEdge(Graphics2D g2d)
    {

        final GeneralPath path = new GeneralPath();
        path.moveTo(A.getX(), A.getY());
        path.curveTo(A.getX()+5.0, A.getY()+5.0, B.getX()-5.0, B.getY()-5.0, B.getX(), B.getY());
        g2d.draw(path);

    }
}
