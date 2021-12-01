package main.java.VisualEditor.EditorView.Edge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class EdgeComponent extends JComponent
{

    private Point2D A, B;
    private String label;

    public EdgeComponent(Point2D A, Point2D B)
    {
        this.A = A;
        this.B = B;
        label = null;
    }

    public EdgeComponent(Point2D A, Point2D B, String label)
    {
        this.A = A;
        this.B = B;
        this.label = label;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        drawEdge(g2D);
    }

    public void drawEdge(Graphics2D g2d)
    {
        System.out.println("Drawing edge: " + A + B);
        final GeneralPath path = new GeneralPath();
        path.moveTo(A.getX(), A.getY());
        path.curveTo(A.getX()+50.0, A.getY(), B.getX()-50.0, B.getY(), B.getX(), B.getY());
        g2d.draw(path);

    }

    public void drawEdge(Graphics2D g2d, Point2D A, Point2D B)
    {
        // Add offset to the node coordinates
        A = new Point((int)(A.getX())+30, (int)(A.getY()+30));
        B = new Point((int)(B.getX())+30, (int)(B.getY()+30));

        final GeneralPath path = new GeneralPath();
        path.moveTo(A.getX(), A.getY());
        path.curveTo(A.getX()+50.0, A.getY(), B.getX()-50.0, B.getY(), B.getX(), B.getY());
        g2d.draw(path);

    }
}
