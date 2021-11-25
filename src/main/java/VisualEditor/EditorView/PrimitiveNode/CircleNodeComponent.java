package main.java.VisualEditor.EditorView.PrimitiveNode;

import javax.swing.*;
import java.awt.*;

public class CircleNodeComponent extends JPanel
{

    private final int size = 60;
    private final CircleNodeGraphics node;
    private final int ID;
    private int x;
    private int y;

    public CircleNodeComponent(String label, int ID)
    {
        this.x = 0;
        this.y = 0;
        this.ID = ID;
        node = new CircleNodeGraphics(x, y, size, label);

    }

    public CircleNodeComponent(int x, int y, String label, int ID)
    {
        this.x = x;
        this.y = y;
        this.ID = ID;
        node = new CircleNodeGraphics(x, y, size, label);

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

}
