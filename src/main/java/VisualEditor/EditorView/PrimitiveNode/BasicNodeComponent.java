package main.java.VisualEditor.EditorView.PrimitiveNode;

import main.java.interfaces.iNode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BasicNodeComponent extends JPanel implements NodeComponent
{

    private final int size = 60;
    private final BasicNodeGraphics node;
    private List<Integer> childrenIDs;
    private final int ID;
    private int x;
    private int y;

    public BasicNodeComponent(String label, int ID)
    {
        this.x = 0;
        this.y = 0;
        this.ID = ID;
        node = new BasicNodeGraphics(x, y, size, label);
    }

    public BasicNodeComponent(int x, int y, String label, int ID)
    {
        this.x = x;
        this.y = y;
        this.ID = ID;
        node = new BasicNodeGraphics(x, y, size, label);

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawComponents(g, x, y);

    }

    public void drawComponents(Graphics g, int x, int y)
    {
        this.x = x;
        this.y = y;
        node.drawOuter((Graphics2D) g, x, y);
        node.drawInner((Graphics2D) g, x, y);
        node.drawLabel((Graphics2D) g, x, y);

    }

    public void drawComponents(Graphics g)
    {

        node.drawOuter((Graphics2D) g, x, y);
        node.drawInner((Graphics2D) g, x, y);
        node.drawLabel((Graphics2D) g, x, y);

    }

    public void setOutC(Color ouC) {
        node.setOutC(ouC);
    }

    public void setInC(Color inC) {
        node.setInC(inC);
    }

    public void setChildrenIDs(List<iNode> childrenNodes)
    {

        if(!childrenNodes.isEmpty())
        {
            childrenIDs = new ArrayList<>();
            for(iNode n : childrenNodes)
            {
                childrenIDs.add(n.getId().hashCode());
            }
        }

    }

    public List<Integer> getChildrenIDs() {
        return childrenIDs;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Point2D getCoords() {
        return new Point(x, y);
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public Dimension2D getDimension() {
        return new Dimension(size, size);
    }
}
