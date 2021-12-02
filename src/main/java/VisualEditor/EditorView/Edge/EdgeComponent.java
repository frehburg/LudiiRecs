package main.java.VisualEditor.EditorView.Edge;

import main.java.VisualEditor.EditorView.VisualUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import static main.java.VisualEditor.EditorView.PrimitiveNode.BasicNodeComponent.NODE_SIZE;

public class EdgeComponent extends JComponent
{

    private final Point2D A;
    private final Point2D B;
    private final String label;

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
        A = VisualUtils.getPointCentered(A, NODE_SIZE, NODE_SIZE);
        B = VisualUtils.getPointCentered(B, NODE_SIZE, NODE_SIZE);

        final GeneralPath path = new GeneralPath();
        path.moveTo(A.getX(), A.getY());
        path.curveTo(A.getX()+50.0, A.getY(), B.getX()-50.0, B.getY(), B.getX(), B.getY());
        g2d.draw(path);

    }
}
