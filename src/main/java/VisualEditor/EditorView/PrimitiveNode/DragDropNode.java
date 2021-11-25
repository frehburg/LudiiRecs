package main.java.VisualEditor.EditorView.PrimitiveNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

public class DragDropNode extends JComponent {

    private CircleNodeComponent node;
    private Point nodeCorner;
    private Point prevPt;
    private int radius;
    private Shape bound;

    private Color outC = Color.gray;
    private Color inC = Color.red;


    public DragDropNode(int x, int y, int radius) {
        node = new CircleNodeComponent(x,y,radius*2, radius*2, outC, inC);
        this.radius = radius;
        bound = new Ellipse2D.Double(x, y, radius*2, radius*2);
        nodeCorner = new Point(x,y);
        prevPt = new Point(x, y);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        node.drawComponents(g, (int) nodeCorner.getX(), (int) nodeCorner.getY());
    }

    public class ClickListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (inBound(e.getPoint())) {
                prevPt = e.getPoint();
            }
        }

    }

    public class DragListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            if (inBound(e.getPoint())) {
                Point crPt = e.getPoint();
                nodeCorner.translate(
                        (int) (crPt.getX()-prevPt.getX()),
                        (int) (crPt.getY()-prevPt.getY())
                );
                prevPt = crPt;
                repaint();
                revalidate();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (inBound(e.getPoint())) {
                node.setInC(Color.LIGHT_GRAY);
            }
            else {
                node.setInC(Color.red);
            }
            repaint();
            revalidate();
        }
    }

    public boolean inBound(Point e) {
        bound = new Ellipse2D.Double(nodeCorner.getX(), nodeCorner.getY(), radius*2, radius*2);
        return bound.contains(e);
    }

}
