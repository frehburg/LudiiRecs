package main.java.VisualEditor.EditorView.PrimitiveNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

public class DragDropNode extends JPanel
{

    private final CircleNodeComponent node;
    private final Point nodeCorner;
    private Point prevPt;
    private final int radius = 50;
    private Shape bound;

    private String label;

    public DragDropNode(String label)
    {
        node = new CircleNodeComponent(label, 0);

        bound = new Ellipse2D.Double(0, 0, radius*2, radius*2);
        nodeCorner = new Point(0,0);
        prevPt = new Point(0, 0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        node.drawComponents(g, (int) nodeCorner.getX(), (int) nodeCorner.getY());
    }

    public class ClickListener extends MouseAdapter
    {

        public void mousePressed(MouseEvent e)
        {
            if (inBound(e.getPoint()))
            {
                prevPt = e.getPoint();
            }
        }

    }

    public class DragListener extends MouseMotionAdapter
    {

        public void mouseDragged(MouseEvent e)
        {
            if (inBound(e.getPoint()))
            {
                Point crPt = e.getPoint();
                nodeCorner.translate
                        (
                        (int) (crPt.getX()-prevPt.getX()),
                        (int) (crPt.getY()-prevPt.getY())
                        );
                prevPt = crPt;
                repaint();
                revalidate();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if (inBound(e.getPoint()))
            {
                node.setInC(Color.LIGHT_GRAY);
            }
            else
            {
                node.setInC(Color.red);
            }
            repaint();
            revalidate();
        }
    }

    public int getX()
    {
        return (int) (nodeCorner.getX()+radius/2);
    }

    public int getY()
    {
        return (int) (nodeCorner.getY()+radius/2);
    }

    public boolean inBound(Point e)
    {
        bound = new Ellipse2D.Double(nodeCorner.getX(), nodeCorner.getY(), radius*2, radius*2);
        return bound.contains(e);
    }

}
